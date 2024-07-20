package com.telemedicicne.telemedicicne.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "userId")
@Entity

public class HealthOfficer implements UserDetails {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long docHsId;

    private String name;
    @NaturalId(mutable = true)
    @Column(unique = true) // Ensure uniqueness

    private String email;
    private String mobileNo;
    private String specialist;
    private String password;
    private String type;//Health officer or Doctor

    @Lob
    @Column(columnDefinition = "TEXT")
    private String videoToken;
//    private Long refId;

//    private String sessionId;
//    private String currentCallToken;
//    private String currentCallStatus;

    public void receiveVideoTokenFromDoctor(String token, Doctor sender) {
        this.videoToken = token;
        this.doctor = sender;
    }


    @OneToMany(mappedBy = "healthOfficer", cascade = CascadeType.ALL)
    private Set<PatientHealthMetrics> patientHealthMetrics = new HashSet<>();

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();
//    @ManyToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
//    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "healthOfficer", cascade = CascadeType.ALL)
    private Set<Patient> patients = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;


//    @ManyToMany
//    @JoinTable(
//            name = "doctor_health_officer",
//            joinColumns = @JoinColumn(name = "health_officer_id"),
//            inverseJoinColumns = @JoinColumn(name = "doctor_id")
//    )
//    private Set<Doctor> doctors = new HashSet<>();


//    @ManyToOne
//    @JoinColumn(name = "doctor_id")
//    private Doctor doctor;

//    @OneToOne(mappedBy = "healthOfficer", cascade = CascadeType.ALL)
//    @JoinColumn(name = "doctor_id")
//    private Doctor doctor;
@ManyToOne
@JoinColumn(name = "doctor_id")
private Doctor doctor;

//@ManyToMany
//@JoinTable(
//        name = "doc_hs_roles",
//        joinColumns = @JoinColumn(name = "doc_hs_id"),
//        inverseJoinColumns = @JoinColumn(name = "role_id")
//)
//private Set<Role> roles = new HashSet<>();

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return List.of();
//    }
@Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    return roles.stream()
            .map(role -> new SimpleGrantedAuthority(role.getName()))
            .collect(Collectors.toList());
}

//    @Override
//    public String getUsername() {
//        return this.email;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Implement logic for account expiration if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Implement logic for account locking if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Implement logic for credentials expiration if needed
    }

    @Override
    public boolean isEnabled() {
        return true; // Implement logic for enabled/disabled accounts
    }

    @OneToOne(mappedBy = "healthOfficer", cascade = CascadeType.ALL)
    private RefreshToken refreshToken;
}
