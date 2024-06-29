package com.upc.bikefastgo.dto;

import com.upc.bikefastgo.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BicycleDto {
    private Long id;
    private String bicycleName;
    private String bicycleDescription;
    private double bicyclePrice;
    private String bicycleSize;
    private String bicycleModel;
    private String imageData;
    private Double latitudeData;
    private Double longitudeData;
    private User user;
}
