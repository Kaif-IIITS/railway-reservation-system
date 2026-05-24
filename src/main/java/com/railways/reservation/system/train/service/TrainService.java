package com.railways.reservation.system.train.service;

import com.railways.reservation.system.seat.entity.Seat;
import com.railways.reservation.system.train.dto.SeatResponse;

import java.util.List;

public interface TrainService {
    void addTrain(String trainName, String trainCode);
    List<SeatResponse> getSeatsByTrainId(Long trainId);
}
