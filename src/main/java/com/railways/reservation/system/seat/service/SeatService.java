package com.railways.reservation.system.seat.service;

import com.railways.reservation.system.seat.dto.SeatRequest;

public interface SeatService {
    void addSeat(SeatRequest request);
    void deleteSeat(Long seatId);
}
