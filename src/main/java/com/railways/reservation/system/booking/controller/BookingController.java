package com.railways.reservation.system.booking.controller;

import com.railways.reservation.system.booking.dto.request.IssueTicketRequest;
import com.railways.reservation.system.booking.dto.response.TicketResponse;
import com.railways.reservation.system.booking.service.BookingService;
import com.railways.reservation.system.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;

    @GetMapping("/my")
    public ResponseEntity<List<TicketResponse>> getMyBookings(
            Authentication authentication
    ) {

        User user = (User) authentication.getPrincipal();

        List<TicketResponse> response =
                bookingService.myBookings(user.getId());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketResponse> getBookingById(
            @PathVariable Long id,
            Authentication authentication
    ) {
        return ResponseEntity.ok(bookingService.bookingById(id));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TicketResponse>> getUserBookings(
            @PathVariable Long userId
    ) {

        List<TicketResponse> response =
                bookingService.myBookings(userId);

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<TicketResponse> bookTicket(@Valid @RequestBody IssueTicketRequest request) {
        TicketResponse response = bookingService.bookTicket(request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable("id") Long bookingId, Authentication authentication) {
        User user  = (User) authentication.getPrincipal();
        bookingService.cancelBooking(bookingId,user.getId());
        return ResponseEntity.noContent().build();
    }

}
