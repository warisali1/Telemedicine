package com.telemedicicne.telemedicicne.Dtos;



import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class VideoTokenRequestDTO {
    private String videoToken;
//    private Map<String, Object> videoToken; // Change from String to Map

    private Long refId;
}
