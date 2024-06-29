package com.upc.bikefastgo.controller;

import com.upc.bikefastgo.model.Availability;
import com.upc.bikefastgo.service.AvailabilityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@Tag(name = "AvailabilityController", description = "the availability API")

@RestController
@RequestMapping("/api/bikefastgo/v1")
public class AvailabilityController {
    private final AvailabilityService availabilityService;

    public AvailabilityController(AvailabilityService availabilityService) {
        this.availabilityService = availabilityService;
    }

    // URL: http://localhost:8080/api/bikefastgo/v1/availabilities/{availabilityId}
    // Method: GET
    @Transactional(readOnly = true)
    @GetMapping("/availabilities/{availabilityId}")
    public ResponseEntity<Availability> getAvailabilityById(@PathVariable(name = "availabilityId") Long availabilityId) {
        return new ResponseEntity<Availability>(availabilityService.getById(availabilityId), HttpStatus.OK);
    }
}
