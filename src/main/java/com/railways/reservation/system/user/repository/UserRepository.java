package com.railways.reservation.system.user.repository;

import com.railways.reservation.system.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long > {
    boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
}
