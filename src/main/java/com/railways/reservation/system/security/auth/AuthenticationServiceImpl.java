package com.railways.reservation.system.security.auth;

import com.railways.reservation.system.exception.BusinessException;
import com.railways.reservation.system.role.Role;
import com.railways.reservation.system.role.RoleRepository;
import com.railways.reservation.system.security.JwtService;
import com.railways.reservation.system.security.dto.request.AuthenticationRequest;
import com.railways.reservation.system.security.dto.request.RefreshRequest;
import com.railways.reservation.system.security.dto.request.RegistrationRequest;
import com.railways.reservation.system.security.dto.response.AuthenticationResponse;
import com.railways.reservation.system.user.entity.User;
import com.railways.reservation.system.user.entity.UserMapper;
import com.railways.reservation.system.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.railways.reservation.system.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService{

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional
    public AuthenticationResponse login(AuthenticationRequest request){

        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = (User) auth.getPrincipal();
        String accessToken = jwtService.generateAccessToken(user.getEmail());
        String refreshToken = jwtService.generateRefreshToken(user.getEmail());
        String tokenType = "Bearer";
        log.info("{} logged in successfully.", user.getEmail());
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType(tokenType)
                .build();
    }

    @Override
    @Transactional
    public void register(RegistrationRequest request){
        checkEmail(request.getEmail());
        checkPhoneNumber(request.getPhoneNumber());
        verifyPassword(request.getPassword(), request.getConfirmPassword());
        Role role = roleRepository.findByName("ROLE_USER").orElseThrow(() -> new BusinessException(ROLE_NOT_FOUND));
        log.error("Role not found: {}", "ROLE_USER");

        User user = userMapper.toUser(request);
        user.addRole(role);

        userRepository.save(user);
    }

    @Override
    @Transactional
    public AuthenticationResponse refreshToken(RefreshRequest request){
        String accessToken = jwtService.refreshAccessToken(request.getRefreshToken());
        String tokenType = "Bearer";
        return AuthenticationResponse.builder()
                .accessToken(accessToken)
                .refreshToken(request.getRefreshToken())
                .tokenType(tokenType)
                .build();
    }

    private void checkEmail(String email) {
        if(userRepository.existsByEmailIgnoreCase(email)){
            throw new BusinessException(EMAIL_ALREADY_EXISTS);
        }
    }

    private void checkPhoneNumber(String phoneNumber){
        if(userRepository.existsByPhoneNumber(phoneNumber)){
            throw new BusinessException(PHONE_ALREADY_EXISTS);
        }
    }

    private void verifyPassword(String password, String confirmPassword){
        {
            if(!password.equals(confirmPassword)){
                throw new BusinessException(PASSWORD_MISMATCH);
            }
        }
    }
}