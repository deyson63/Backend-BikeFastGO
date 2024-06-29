package com.upc.bikefastgo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDtoResponse {
    private Long id;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userPhone;
    private LocalDate userBirthDate;
    private Double latitudeData;
    private Double longitudeData;
    private String imageData;
}
