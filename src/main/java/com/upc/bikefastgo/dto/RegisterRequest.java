package com.upc.bikefastgo.dto;

import com.upc.bikefastgo.model.Roles;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userPassword;
    private String userPhone;
    private LocalDate userBirthDate;
    private Double latitudeData;
    private Double longitudeData;
    private String imageData;
    private Roles role;
}
