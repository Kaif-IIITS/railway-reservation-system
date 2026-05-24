package com.railways.reservation.system.security.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationRequest {

    @NotBlank(message = "First name must not be blank")
    private String firstName;

    @NotBlank(message = "Last name must not be blank")
    private String lastName;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 5, message = "Password must be at least 5 characters long")
    private String password;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 5, message = "Password must be at least 5 characters long")
    private String confirmPassword;

    @NotBlank(message = "Phone number must not be blank")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Phone number must be valid")
    private String phoneNumber;
}