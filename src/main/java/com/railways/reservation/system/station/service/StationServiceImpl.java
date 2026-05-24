package com.railways.reservation.system.station.service;

import com.railways.reservation.system.exception.BusinessException;
import com.railways.reservation.system.station.entity.Station;
import com.railways.reservation.system.station.repository.StationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.railways.reservation.system.exception.ErrorCode.STATION_ALREADY_EXISTS;

@Service
@RequiredArgsConstructor
public class StationServiceImpl implements StationService {

    private final StationRepository stationRepository;

    @Override
    @Transactional
    public void addStation(String stationName, String stationCode) {

        if(stationRepository.existsByCode(stationCode)){
            throw new BusinessException(STATION_ALREADY_EXISTS);
        }

        stationRepository.save(Station.builder()
                .name(stationName)
                .code(stationCode).build());
    }
}
