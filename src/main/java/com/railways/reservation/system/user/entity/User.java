package com.railways.reservation.system.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.railways.reservation.system.booking.entity.Booking;
import com.railways.reservation.system.config.BaseEntity;
import com.railways.reservation.system.role.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class User extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "first_name")
    @NotBlank(message = "First name is required")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Last name is required")
    private String lastName;

    @Column(name = "phone_number")
    @Pattern(regexp = "^\\+?[1-9]\\d{1,14}$", message = "Invalid phone number format")
    private String phoneNumber;

    @Column(name = "phone_number_verified", nullable = false)
    private boolean phoneNumberVerified;

    @Column(name = "email", unique = true, nullable = false)
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    private String email;

    @Column(name = "password" , nullable = false)
    @NotBlank(message = "password is required")
    @Length(min = 5 , message = "Password must be at least 5 characters long")
    private String password;

    @Column(name = "DATE_OF_BIRTH")
    private LocalDate dateOfBirth;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

    @Column(name = "credentials_expired", nullable = false)
    private boolean credentialExpired ;

    @Column(name = "account_locked", nullable = false)
    private boolean accountLocked ;

    @Column(name = "email_verified", nullable = false)
    private boolean emailVerified ;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.DETACH})
    @JoinTable(
            name = "USERS_ROLES",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id")
    )
    private List<Role> roles;

    public void addRole(Role role){
        if(roles==null){
            roles=new ArrayList<>();
        }
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role){
        this.roles.remove(role);
        role.getUsers().remove(this);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if(CollectionUtils.isEmpty(roles)){
            return List.of();
        }
        return roles.stream().map(
                r-> new SimpleGrantedAuthority(r.getName())).toList();
    }


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL,orphanRemoval = true)
    private List<Booking> bookings;

    public void addBooking(Booking booking){
        if(booking == null){
            bookings = new ArrayList<>();
        }
        bookings.add(booking);
        booking.setUser(this);
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !this.accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !this.credentialExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}