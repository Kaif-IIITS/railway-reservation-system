package com.railways.reservation.system.user.service;

import com.railways.reservation.system.exception.BusinessException;
import com.railways.reservation.system.user.entity.User;
import com.railways.reservation.system.user.dto.ChangePasswordRequest;
import com.railways.reservation.system.user.dto.ProfileUpdateRequest;
import com.railways.reservation.system.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.railways.reservation.system.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username){
        return userRepository.findByEmailIgnoreCase(username).orElseThrow(() -> new BusinessException(USER_NOT_FOUND,username));
    }

    @Override
    @Transactional
    public void updateProfileInfo(ProfileUpdateRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new BusinessException(USER_NOT_FOUND));

        if(request.getFirstName() != null && !request.getFirstName().isBlank()){
            user.setFirstName(request.getFirstName());
        }

        if(request.getLastName() != null && !request.getLastName().isBlank()){
            user.setLastName(request.getLastName());
        }

        if(request.getDateOfBirth() != null){
            user.setDateOfBirth(request.getDateOfBirth());
        }
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new BusinessException(USER_NOT_FOUND));
        if (!request.getNewPassword()
                .equals(request.getConfirmNewPassword())) {
            throw new BusinessException(CHANGE_PASSWORD_MISMATCH);
        }
        if(!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())){
            throw new BusinessException(INVALID_CURRENT_PASSWORD);
        }
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deactivateAccount(Long userId) {

        final User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND));

        if (!user.isEnabled()) {
            throw new BusinessException(ACCOUNT_ALREADY_DEACTIVATED);
        }

        user.setEnabled(false);
        this.userRepository.save(user);
    }

    @Override
    @Transactional
    public void reactivateAccount(Long userId) {
        final User user = this.userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND));

        if (user.isEnabled()) {
            throw new BusinessException(ACCOUNT_ALREADY_ACTIVATED);
        }

        user.setEnabled(true);
        this.userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteAccount(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND));

        userRepository.delete(user);
    }
}
