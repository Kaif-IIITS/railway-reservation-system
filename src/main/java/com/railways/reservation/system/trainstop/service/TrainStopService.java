package com.railways.reservation.system.trainstop.service;

import com.railways.reservation.system.trainstop.dto.TrainStopRequest;
import com.railways.reservation.system.trainstop.dto.TrainStopUpdateRequest;

public interface TrainStopService {

    void addTrainStop(TrainStopRequest trainStopRequest);
    void updateTrainStop(Long id, TrainStopUpdateRequest trainStopUpdateRequest);
    void deleteTrainStop(Long id);
}
