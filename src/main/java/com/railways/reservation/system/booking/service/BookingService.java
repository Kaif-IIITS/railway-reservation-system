package com.railways.reservation.system.booking.service;

import com.railways.reservation.system.booking.dto.request.IssueTicketRequest;
import com.railways.reservation.system.booking.dto.response.TicketResponse;

import java.util.List;

public interface BookingService {
    TicketResponse bookTicket(IssueTicketRequest request);
    void cancelBooking(Long bookingId, Long id);
    List<TicketResponse> myBookings(Long userId);
    TicketResponse bookingById(Long bookingId);
}
