package com.upc.bikefastgo.service;

import com.upc.bikefastgo.dto.RentDto;
import com.upc.bikefastgo.model.Rent;

import java.util.List;

public interface RentService {
    public abstract Rent create(RentDto rent);
    public abstract Rent getById(Long rent_id);
    public abstract void delete(Long rent_id);

    public abstract List<Rent> getByBicycleId(Long bicycle_id);
}
