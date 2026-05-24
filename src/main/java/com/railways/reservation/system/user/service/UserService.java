package com.railways.reservation.system.user.service;

import com.railways.reservation.system.user.dto.ChangePasswordRequest;
import com.railways.reservation.system.user.dto.ProfileUpdateRequest;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    void updateProfileInfo(ProfileUpdateRequest request, Long userId);

    void changePassword(ChangePasswordRequest request, Long userId);

    void deactivateAccount(Long userId);

    void reactivateAccount(Long userId);

    void deleteAccount(Long id);

}
