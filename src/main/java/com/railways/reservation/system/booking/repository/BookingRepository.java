package com.railways.reservation.system.booking.repository;

import com.railways.reservation.system.booking.entity.Booking;
import com.railways.reservation.system.booking.entity.BookingStatus;
import com.railways.reservation.system.train.entity.Train;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking,Long> {
    List<Booking> findByUserId(Long userId);
    List<Booking> findByTrainIdAndJourneyDateAndBookingStatus(Long id, LocalDate journeyDate, BookingStatus bookingStatus);
    Optional<Booking> findByIdAndUserId(Long bookingId, Long userId);
}
