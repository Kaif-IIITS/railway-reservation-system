package com.railways.reservation.system.booking.dto.request;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class IssueTicketRequest {

    @NotNull(message = "User id is required")
    @Min(value = 1, message = "User id must be positive")
    private Long userId;

    @NotNull(message = "Train id is required")
    @Min(value = 1, message = "Train id must be positive")
    private Long trainId;

    @NotNull(message = "Source station id is required")
    @Min(value = 1, message = "Source station id must be positive")
    private Long sourceStationId;

    @NotNull(message = "Destination station id is required")
    @Min(value = 1, message = "Destination station id must be positive")
    private Long destinationStationId;

    @NotNull(message = "Journey date is required")
    @FutureOrPresent(message = "Journey date cannot be in the past")
    private LocalDate journeyDate;
}