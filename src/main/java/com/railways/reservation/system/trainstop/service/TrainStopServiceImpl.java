package com.railways.reservation.system.trainstop.service;

import com.railways.reservation.system.exception.BusinessException;
import com.railways.reservation.system.station.entity.Station;
import com.railways.reservation.system.station.repository.StationRepository;
import com.railways.reservation.system.train.entity.Train;
import com.railways.reservation.system.train.repository.TrainRepository;
import com.railways.reservation.system.trainstop.dto.TrainStopRequest;
import com.railways.reservation.system.trainstop.dto.TrainStopUpdateRequest;
import com.railways.reservation.system.trainstop.entity.TrainStop;
import com.railways.reservation.system.trainstop.repository.TrainStopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.railways.reservation.system.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class TrainStopServiceImpl implements TrainStopService{

    private final TrainStopRepository trainStopRepository;
    private final TrainRepository trainRepository;
    private final StationRepository stationRepository;

    @Override
    @Transactional
    public void addTrainStop(TrainStopRequest request) {
        Train train = trainRepository.findById(request.getTrainId()).orElseThrow(()->new BusinessException(TRAIN_NOT_EXISTS));
        Station station = stationRepository.findById(request.getStationId()).orElseThrow(()->new BusinessException( STATION_NOT_EXISTS));

        if(trainStopRepository.existsByTrainAndStation(train,station)){
            throw new BusinessException(STOP_ALREADY_EXISTS);
        }

        TrainStop trainStop = TrainStop.builder().
                train(train)
                .station(station)
                .stopOrder(request.getStopOrder())
                .arrivalTime(request.getArrivalTime())
                .departureTime(request.getDepartureTime())
                .build();
        trainStopRepository.save(trainStop);
    }

    @Override
    @Transactional
    public void updateTrainStop(Long id, TrainStopUpdateRequest request) {
        TrainStop trainStop = trainStopRepository.findById(id).orElseThrow(()->new BusinessException((STOP_NOT_EXISTS)));
        
        if(request.getArrivalTime() != null){
            trainStop.setArrivalTime(request.getArrivalTime());
        }
        
        if(request.getDepartureTime() != null){
            trainStop.setDepartureTime(request.getDepartureTime());
        }
        
        trainStopRepository.save(trainStop);
    }

    @Override
    @Transactional
    public void deleteTrainStop(Long id) {
        TrainStop trainStop = trainStopRepository.findById(id).orElseThrow(()->new BusinessException(STOP_NOT_EXISTS));
        trainStopRepository.delete(trainStop);
    }
}