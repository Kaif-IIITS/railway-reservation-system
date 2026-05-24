package com.railways.reservation.system.train.repository;


import com.railways.reservation.system.train.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainRepository extends JpaRepository<Train, Long> {
    boolean existsByCode(String code);
}
