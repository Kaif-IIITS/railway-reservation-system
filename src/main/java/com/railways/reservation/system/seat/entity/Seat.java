package com.railways.reservation.system.seat.entity;

import com.railways.reservation.system.booking.entity.Booking;
import com.railways.reservation.system.config.BaseEntity;
import com.railways.reservation.system.train.entity.Train;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "seats")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class Seat extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "coach_number",nullable = false)
    private String coachNumber;

    @Column(name = "seat_number",nullable = false)
    private Integer seatNumber;

    @Column(name = "seat_type",nullable = false)
    @Enumerated(EnumType.STRING)
    private SeatType seatType;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "modified_by",insertable = false)
    private String modifiedBy;

    @ManyToOne
    @JoinColumn(name = "train_id",nullable = false)
    private Train train;

    @OneToMany(mappedBy = "seat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;

    public void addBooking(Booking booking){
        if(bookings == null){
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
        booking.setSeat(this);
    }
}
