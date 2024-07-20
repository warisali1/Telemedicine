package com.telemedicicne.telemedicicne.Dtos;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class DoctorDTO {
    private Long id;
    private String name;
    private String email;
    private String mobileNo;

    public DoctorDTO(Long id,String name, String email, String mobileNo) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobileNo = mobileNo;
    }

    // Getters and Setters
}
