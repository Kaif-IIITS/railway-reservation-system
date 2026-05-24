package com.railways.reservation.system.security.auth;

import com.railways.reservation.system.security.dto.request.AuthenticationRequest;
import com.railways.reservation.system.security.dto.request.RefreshRequest;
import com.railways.reservation.system.security.dto.response.AuthenticationResponse;
import com.railways.reservation.system.security.dto.request.RegistrationRequest;

public interface AuthenticationService {
    AuthenticationResponse login(AuthenticationRequest request);

    void register(RegistrationRequest request);

    AuthenticationResponse refreshToken(RefreshRequest req);
}
