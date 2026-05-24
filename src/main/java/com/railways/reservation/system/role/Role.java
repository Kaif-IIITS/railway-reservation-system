package com.railways.reservation.system.role;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.railways.reservation.system.config.BaseEntity;
import com.railways.reservation.system.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Entity
@Table(name = "roles")
@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role extends BaseEntity {

    @Id
    @Column(name = "name")
    private String name;

    @JsonIgnore
    @ManyToMany(mappedBy = "roles")
    private List<User> users;
}