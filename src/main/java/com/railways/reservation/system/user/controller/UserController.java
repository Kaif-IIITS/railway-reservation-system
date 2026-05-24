package com.railways.reservation.system.user.controller;

import com.railways.reservation.system.user.entity.User;
import com.railways.reservation.system.user.dto.ChangePasswordRequest;
import com.railways.reservation.system.user.dto.ProfileUpdateRequest;
import com.railways.reservation.system.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PutMapping("/me")
    public ResponseEntity<Void> updateProfile(@RequestBody ProfileUpdateRequest request, final Authentication authentication){
        userService.updateProfileInfo(request, getId(authentication));
        return ResponseEntity.ok().build();
    }

    @PutMapping("/me/password")
    public ResponseEntity<Void> changePassword(@RequestBody ChangePasswordRequest request, final Authentication authentication){
        userService.changePassword(request, getId(authentication));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/me/reactivate")
    public ResponseEntity<Void> reactivateAccount(final Authentication authentication){
        userService.reactivateAccount(getId(authentication));
        return ResponseEntity.ok().build();
    }

    @PostMapping("/me/deactivate")
    public ResponseEntity<Void> deactivateAccount(final Authentication authentication){
        userService.deactivateAccount(getId(authentication));
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteAccount(final Authentication authentication){
        userService.deleteAccount(getId(authentication));
        return ResponseEntity.ok().build();
    }

    private Long getId(Authentication auth){
        return ((User)(auth.getPrincipal())).getId();
    }
}
