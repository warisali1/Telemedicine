package com.telemedicicne.telemedicicne.Dtos;

import com.telemedicicne.telemedicicne.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DoctorHoDetailsDTO {
    private Long id;
    private String name;
    private String specialist;
//    private Set<Role> role;
private String role; // Change type to String

}
