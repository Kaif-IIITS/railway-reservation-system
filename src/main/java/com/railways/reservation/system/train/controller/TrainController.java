package com.railways.reservation.system.train.controller;

import com.railways.reservation.system.train.dto.SeatResponse;
import com.railways.reservation.system.train.dto.TrainRequest;
import com.railways.reservation.system.train.service.TrainService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/trains")
@RequiredArgsConstructor
public class TrainController {

    private final TrainService trainService;

    @PostMapping
    public ResponseEntity<Void> addTrain(@Valid @RequestBody List<TrainRequest> request){
        for(TrainRequest train : request) trainService.addTrain(train.getName(), train.getCode());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/seats")
    public ResponseEntity<List<SeatResponse>> getSeatByTrainId(@PathVariable Long id){
        return ResponseEntity.ok(trainService.getSeatsByTrainId(id));
    }
}
