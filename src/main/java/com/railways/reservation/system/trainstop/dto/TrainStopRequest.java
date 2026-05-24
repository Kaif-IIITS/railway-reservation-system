package com.railways.reservation.system.trainstop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TrainStopRequest {

    @NotNull(message = "Train ID is required")
    private Long trainId;
    @NotNull(message = "Station ID is required")
    private Long stationId;
    @NotNull(message = "Stop order is required")
    private Integer stopOrder;
    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;
    @NotNull(message = "Departure time is required")
    private LocalDateTime departureTime;
}
