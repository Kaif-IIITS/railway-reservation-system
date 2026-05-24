package com.railways.reservation.system.security.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AuthenticationRequest {
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Must be a valid")
    private String email;

    @NotBlank(message = "Password must not be blank")
    private String password;
}
