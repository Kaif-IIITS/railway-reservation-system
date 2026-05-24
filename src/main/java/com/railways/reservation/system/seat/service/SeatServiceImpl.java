package com.railways.reservation.system.seat.service;

import com.railways.reservation.system.exception.BusinessException;
import com.railways.reservation.system.seat.dto.SeatRequest;
import com.railways.reservation.system.seat.entity.Seat;
import com.railways.reservation.system.seat.repository.SeatRepository;
import com.railways.reservation.system.train.entity.Train;
import com.railways.reservation.system.train.repository.TrainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.railways.reservation.system.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class SeatServiceImpl implements SeatService{

    private final SeatRepository seatRepository;
    private final TrainRepository trainRepository;

    @Override
    @Transactional
    public void addSeat(SeatRequest request){
        Train train = trainRepository.findById(request.getTrainId()).orElseThrow(()->new BusinessException(TRAIN_NOT_EXISTS));
        if(seatRepository.existsByCoachNumberAndSeatNumberAndTrain(request.getCoachNumber(), request.getSeatNumber(),train)){
            throw new BusinessException(SEAT_ALREADY_EXISTS);
        }
        seatRepository.save(Seat.builder().coachNumber(request.getCoachNumber()).seatNumber(request.getSeatNumber()).seatType(request.getSeatType()).train(train).build());
    }

    @Override
    @Transactional
    public void deleteSeat(Long seatId) {
        if(!seatRepository.existsById(seatId)){
            throw new BusinessException(SEAT_NOT_EXISTS);
        }
        seatRepository.deleteById(seatId);
    }
}
