package com.railways.reservation.system.station.entity;

import com.railways.reservation.system.config.BaseEntity;
import com.railways.reservation.system.trainstop.entity.TrainStop;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "stations")
@ToString(callSuper = true)
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
public class Station extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "code", unique = true, nullable = false)
    private String code;

    @Column(name = "name", nullable = false)
    private String name;

    @CreatedBy
    @Column(name = "created_by", updatable = false)
    private String createdBy;

    @LastModifiedBy
    @Column(name = "modified_by",insertable = false)
    private String modifiedBy;

    @OneToMany
    @JoinColumn(name = "train_stop")
    private List<TrainStop> trainStop;

    public void addTrainStop(TrainStop trainStop){
        if(this.trainStop == null){
            this.trainStop = new ArrayList<>();
        }
        this.trainStop.add(trainStop);
        trainStop.setStation(this);
    }
}
