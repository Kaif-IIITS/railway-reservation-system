package com.railways.reservation.system.seat.repository;

import com.railways.reservation.system.seat.entity.Seat;
import com.railways.reservation.system.seat.entity.SeatType;
import com.railways.reservation.system.train.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat,Long> {
    boolean existsByCoachNumberAndSeatNumberAndTrain(String coachNumber, Integer seatNumber, Train train);
    List<Seat> findByTrain(Train train);
    List<Seat> findByTrainId(Long id);
}
