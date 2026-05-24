package com.railways.reservation.system.security.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefreshRequest {

    @NotBlank(message = "Refresh token must not be blank")
    @JsonProperty("refresh_token")
    private String refreshToken;
}
