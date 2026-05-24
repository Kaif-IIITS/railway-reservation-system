package com.railways.reservation.system.trainstop.controller;

import com.railways.reservation.system.trainstop.dto.TrainStopRequest;
import com.railways.reservation.system.trainstop.dto.TrainStopUpdateRequest;
import com.railways.reservation.system.trainstop.entity.TrainStop;
import com.railways.reservation.system.trainstop.service.TrainStopService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/train-stops")
public class TrainStopController {

    private final TrainStopService trainStopService;

    @PostMapping
    public ResponseEntity<Void> addTrainStop(@Valid @RequestBody List<TrainStopRequest> trainStopRequest){
        for(TrainStopRequest stop : trainStopRequest)trainStopService.addTrainStop(stop);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateTrainStop(@PathVariable Long id, @RequestBody TrainStopUpdateRequest request){
        trainStopService.updateTrainStop(id, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainStop(@PathVariable Long id){
        trainStopService.deleteTrainStop(id);
        return ResponseEntity.ok().build();
    }
}
