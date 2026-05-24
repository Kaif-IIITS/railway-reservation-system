package com.railways.reservation.system.booking.entity;

import com.railways.reservation.system.seat.entity.Seat;
import com.railways.reservation.system.train.entity.Train;
import com.railways.reservation.system.trainstop.entity.TrainStop;
import com.railways.reservation.system.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false,updatable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "train_id", nullable = false,updatable = false)
    private Train train;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false,updatable = false)
    private Seat seat;

    @ManyToOne
    @JoinColumn(name = "source_stop_id", nullable = false,updatable = false)
    private TrainStop sourceStop;

    @ManyToOne
    @JoinColumn(name = "destination_stop_id", nullable = false,updatable = false)
    private TrainStop destinationStop;

    @Column(name = "booking_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus;

    @Column(name = "journey_date", nullable = false, updatable = false)
    private LocalDate journeyDate;

    @CreatedDate
    @Column(name = "booked_at", nullable = false, updatable = false)
    private LocalDateTime bookedAt;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;
    
    @Column(name = "fare", nullable = false)
    private Integer fare;
}
