package com.railways.reservation.system.train.entity;

import com.railways.reservation.system.booking.entity.Booking;
import com.railways.reservation.system.config.BaseEntity;
import com.railways.reservation.system.seat.entity.Seat;
import com.railways.reservation.system.trainstop.entity.TrainStop;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "trains")
@ToString(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class Train extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code" , nullable = false , unique = true)
    private String code;

    @Column(name = "name" , nullable = false)
    private String name;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "modified_by",insertable = false)
    private String modifiedBy;

    @OneToMany(mappedBy = "train",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrainStop> trainStops;


    public void addTrainStop(TrainStop trainStop){
        if(trainStops == null){
            trainStops = new ArrayList<>();
        }
        this.trainStops.add(trainStop);
        trainStop.setTrain(this);
    }

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats;

    public void addSeat(Seat seat){
        if (seats == null){
            seats = new ArrayList<>();
        }
        seats.add(seat);
        seat.setTrain(this);
    }

    @OneToMany(mappedBy = "train", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Booking> bookings;

    public void addBooking(Booking booking){
        if(bookings == null){
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
        booking.setTrain(this);
    }
}