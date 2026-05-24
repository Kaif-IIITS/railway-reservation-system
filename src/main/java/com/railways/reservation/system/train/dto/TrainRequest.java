package com.railways.reservation.system.train.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainRequest {
    @NotBlank(message = "Train name is required")
    private String name;
    @NotBlank(message = "Train code is required")
    private String code;
}
