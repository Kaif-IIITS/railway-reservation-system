package com.railways.reservation.system.seat.dto;

import com.railways.reservation.system.seat.entity.SeatType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
public class SeatRequest {
    @NotNull(message = "Coach number is required")
    private String coachNumber;
    @NotNull(message = "Seat number is required")
    private Integer seatNumber;
    @NotNull(message = "Seat type is required")
    private SeatType seatType;
    @NotNull(message = "Train ID is required")
    private Long trainId;
}
