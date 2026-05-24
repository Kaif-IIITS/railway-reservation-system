package com.railways.reservation.system.station.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StationRequest {
    @NotBlank(message = "Station name is required")
    private String name;
    @NotBlank(message = "Station name is required")
    private String code;
}
