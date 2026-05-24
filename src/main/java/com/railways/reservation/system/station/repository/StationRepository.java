package com.railways.reservation.system.station.repository;

import com.railways.reservation.system.station.entity.Station;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StationRepository extends JpaRepository<Station, Long > {

    boolean existsByCode(String stationCode);
}
