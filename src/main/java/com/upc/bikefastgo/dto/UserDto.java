package com.upc.bikefastgo.dto;

import com.upc.bikefastgo.model.Bicycle;
import com.upc.bikefastgo.model.Card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    private String userPhone;
    private LocalDate userBirthDate;
    private Double latitudeData;
    private Double longitudeData;
    private String imageData;
    private List<Bicycle> bicycles;
    private List<Card> cards;

}
