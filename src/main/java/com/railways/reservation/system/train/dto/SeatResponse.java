package com.railways.reservation.system.train.dto;

import com.railways.reservation.system.seat.entity.SeatType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SeatResponse {
    private String coachNumber;
    private Integer seatNumber;
    private SeatType seatType;
}
