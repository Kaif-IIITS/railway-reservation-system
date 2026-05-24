package com.railways.reservation.system.trainstop.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TrainStopUpdateRequest {
    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;
}
