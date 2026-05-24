package com.railways.reservation.system.security.controller;

import com.railways.reservation.system.security.auth.AuthenticationService;
import com.railways.reservation.system.security.dto.request.AuthenticationRequest;
import com.railways.reservation.system.security.dto.request.RefreshRequest;
import com.railways.reservation.system.security.dto.request.RegistrationRequest;
import com.railways.reservation.system.security.dto.response.AuthenticationResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@Valid @RequestBody AuthenticationRequest request){
        log.info("Login attempt for email: {}", request.getEmail());
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@Valid @RequestBody RegistrationRequest request){
        authenticationService.register(request);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refresh(@Valid @RequestBody RefreshRequest req){
        return ResponseEntity.ok(authenticationService.refreshToken(req));
    }
}
