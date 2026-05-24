package com.railways.reservation.system.seat.controller;

import com.railways.reservation.system.seat.dto.SeatRequest;
import com.railways.reservation.system.seat.service.SeatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/seats")
@RequiredArgsConstructor
public class SeatController {

    private final SeatService seatService;

    @PostMapping
    public ResponseEntity<Void> addSeats(@Valid @RequestBody List<SeatRequest> request){
        request.forEach(seatService::addSeat);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long id){
        seatService.deleteSeat(id);
        return ResponseEntity.noContent().build();
    }

}
