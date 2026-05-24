package com.railways.reservation.system.train.service;

import com.railways.reservation.system.exception.BusinessException;
import com.railways.reservation.system.seat.entity.Seat;
import com.railways.reservation.system.seat.repository.SeatRepository;
import com.railways.reservation.system.train.dto.SeatResponse;
import com.railways.reservation.system.train.entity.Train;
import com.railways.reservation.system.train.repository.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.railways.reservation.system.exception.ErrorCode.TRAIN_ALREADY_EXISTS;
import static com.railways.reservation.system.exception.ErrorCode.TRAIN_NOT_EXISTS;

@Service
@RequiredArgsConstructor
public class TrainServiceImpl implements TrainService{
    
    private final TrainRepository trainRepository;
    private final SeatRepository seatRepository;

    @Override
    @Transactional
    public void addTrain(String trainName, String trainCode) {
        if(trainRepository.existsByCode(trainCode)){
            throw new BusinessException(TRAIN_ALREADY_EXISTS);
        }
        
        trainRepository.save(Train.builder()
                .name(trainName)
                .code(trainCode).build());
    }

    @Override
    @Transactional
    public List<SeatResponse> getSeatsByTrainId(Long trainId) {
        Train train = trainRepository.findById(trainId).orElseThrow(()->new BusinessException(TRAIN_NOT_EXISTS));
        List<Seat> list= seatRepository.findByTrain(train);

        return list.stream().map(seat -> SeatResponse.builder().coachNumber(seat.getCoachNumber()).seatNumber(seat.getSeatNumber()).seatType(seat.getSeatType()).build()).toList();
    }
}
