package com.railways.reservation.system.trainstop.repository;

import com.railways.reservation.system.station.entity.Station;
import com.railways.reservation.system.train.entity.Train;
import com.railways.reservation.system.trainstop.entity.TrainStop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainStopRepository extends JpaRepository<TrainStop,Long> {
    boolean existsByTrainAndStation(Train train,Station station);
    Optional<TrainStop> findByTrainIdAndStationId(Long trainId,Long stationId);
}
