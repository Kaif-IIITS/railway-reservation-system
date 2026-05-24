package com.railways.reservation.system.booking.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.railways.reservation.system.booking.entity.BookingStatus;
import com.railways.reservation.system.seat.entity.SeatType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
@JsonPropertyOrder({
        "bookingId",
        "trainName",
        "coachNumber",
        "seatNumber",
        "seatType",
        "sourceStation",
        "destinationStation",
        "journeyDate",
        "bookingStatus",
        "fare",
        "bookedAt"
})
public class TicketResponse {

    private Long bookingId;

    private String trainName;

    private String coachNumber;

    private Integer seatNumber;

    private SeatType seatType;

    private String sourceStation;

    private String destinationStation;

    private LocalDate journeyDate;

    private BookingStatus bookingStatus;

    private Integer fare;

    private LocalDateTime bookedAt;
}
