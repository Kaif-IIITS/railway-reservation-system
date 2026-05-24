package com.railways.reservation.system.station.controller;

import com.railways.reservation.system.station.dto.StationRequest;
import com.railways.reservation.system.station.service.StationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stations")
@RequiredArgsConstructor
public class StationController {

    private final StationService stationService;

    @PostMapping
    ResponseEntity<Void> addStation(@Valid @RequestBody List<StationRequest> request, Authentication authentication){
        for(StationRequest station : request )stationService.addStation(station.getName(), station.getCode());
        return ResponseEntity.ok().build();
    }
}
