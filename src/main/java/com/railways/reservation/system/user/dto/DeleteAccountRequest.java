package com.railways.reservation.system.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DeleteAccountRequest {
    private String password;
    private String confirmPassword;
}
