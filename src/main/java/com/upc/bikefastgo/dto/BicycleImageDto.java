package com.upc.bikefastgo.dto;

public class BicycleImageDto {
    private Long bicycleId;
    private byte[] imageBytes;

    public BicycleImageDto(Long bicycleId, byte[] imageBytes) {
        this.bicycleId = bicycleId;
        this.imageBytes = imageBytes;
    }

    public Long getBicycleId() {
        return bicycleId;
    }

    public void setBicycleId(Long bicycleId) {
        this.bicycleId = bicycleId;
    }

    public byte[] getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(byte[] imageBytes) {
        this.imageBytes = imageBytes;
    }
}
