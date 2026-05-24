package com.railways.reservation.system.user.entity;

import com.railways.reservation.system.security.dto.request.RegistrationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User toUser(RegistrationRequest request){
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .enabled(true)
                .accountLocked(false)
                .credentialExpired(false)
                .emailVerified(false)
                .phoneNumberVerified(false)
                .createdAt(LocalDateTime.now())
                .build();
    }
}
