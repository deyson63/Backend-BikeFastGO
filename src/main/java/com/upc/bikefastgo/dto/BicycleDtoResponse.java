package com.upc.bikefastgo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BicycleDtoResponse {
    private Long id;
    private String bicycleName;
    private String bicycleDescription;
    private double bicyclePrice;
    private String bicycleSize;
    private String bicycleModel;
    private String imageData;
    private Double latitudeData;
    private Double longitudeData;
    private UserDtoResponse userDtoResponse;



}
