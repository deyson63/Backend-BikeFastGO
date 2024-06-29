package com.upc.bikefastgo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="bicycles")
public class Bicycle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="bicycle_name", nullable = false, length = 50)
    private String bicycleName;

    @Column(name="bicycle_description", nullable = false, length = 200)
    private String bicycleDescription;

    @Column(name="bicycle_price", nullable = false)
    private double bicyclePrice;

    @Column(name="bicycle_size", nullable = false, length = 10)
    private String bicycleSize;

    @Column(name="bicycle_model", nullable = true, length = 50)
    private String bicycleModel;

    @Column(name="image_data", nullable = true)
    private String imageData;

    @Column(name="latitude_data", nullable = true)
    private Double latitudeData;

    @Column(name="longitude_data", nullable = true)
    private Double longitudeData;

    @JsonIgnore
    @Lob
    private Blob image;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false,
            foreignKey = @ForeignKey(name = "FK_USER_BICYCLE_ID"))
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;


    public Blob getImage() {
        return image;
    }
    public void setImage(Blob image) {
        this.image = image;
    }

}
