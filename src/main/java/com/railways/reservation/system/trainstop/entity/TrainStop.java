package com.railways.reservation.system.trainstop.entity;

import com.railways.reservation.system.booking.entity.Booking;
import com.railways.reservation.system.config.BaseEntity;
import com.railways.reservation.system.station.entity.Station;
import com.railways.reservation.system.train.entity.Train;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "train_stops")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder
public class TrainStop extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "stop_order",nullable = false)
    private Integer stopOrder;

    @Column(name = "arrival_time",nullable = false)
    private LocalDateTime arrivalTime;

    @Column(name = "departure_time",nullable = false)
    private LocalDateTime departureTime;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "modified_by",insertable = false)
    private String modifiedBy;

    @ManyToOne
    @JoinColumn(name = "station_id")
    private Station station;

    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;

    @OneToMany(mappedBy = "sourceStop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> sourceBookings;

    public void addBookingSource(Booking booking){
        if(sourceBookings == null){
            sourceBookings = new ArrayList<>();
        }
        sourceBookings.add(booking);
        booking.setSourceStop(this);
    }

    @OneToMany(mappedBy = "destinationStop", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> destinationBookings;

    public void addBookingDestination(Booking booking){
        if(destinationBookings == null){
            destinationBookings = new ArrayList<>();
        }
        destinationBookings.add(booking);
        booking.setDestinationStop(this);
    }
}
