package com.upc.bikefastgo.repository;

import com.upc.bikefastgo.model.Bicycle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BicycleRepository extends JpaRepository<Bicycle, Long> {
}
