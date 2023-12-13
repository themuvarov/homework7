package com.example.bike.service;

import com.example.bike.model.Bike;
import com.example.bike.model.BikeDto;
import com.example.bike.model.Region;
import com.example.bike.model.RentRequestMessage;
import com.example.bike.repository.BikeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class BikeService {

    @Value(value = "${bike.region}")
    private Region region;

    private final BikeRepository repository;

    public List<String> getAllBikes(Region region) {
        List<String> result = repository.findByRegion(region).stream().map(Bike::getBikeQr).collect(Collectors.toList());
        return result;
    }

    public List<String> getAllBikesOccupied(Region region, boolean isOccupied) {
        List<String> result = repository.findByRegionAndOccupied(region, isOccupied)
                .stream().map(Bike::getBikeQr).collect(Collectors.toList());
        return result;
    }

    @Transactional
    public Long addBike(BikeDto dto) {
        Bike bike =  repository.findByBikeQr(dto.getBikeQr());
        if (bike != null) {
            throw new RuntimeException("Already exists " + dto.getBikeQr());
        } else {
            bike = new Bike();
            bike.setBikeQr(dto.getBikeQr());
            bike.setOccupied(false);
            bike.setRegion(dto.getRegion());
            bike.setOccupiedTill(null);
            repository.save(bike);
        }

        return bike.getId();
    }

    public Boolean checkIfAvailable(RentRequestMessage dto) {
        Bike bike = repository.findByBikeQr(dto.getBikeQr());
        if (bike.getOccupied()) {
            return false;
        }

        return true;
    }

    public Boolean occupyBike(RentRequestMessage dto) {
        Bike bike =  repository.findByBikeQr(dto.getBikeQr());
        if (bike == null) {
            throw new RuntimeException("Not found " + dto.getBikeQr());
        } else {
            bike.setOccupied(true);
            bike.setOccupiedTill(LocalDateTime.now().plusMinutes(dto.getHowLong()));
            repository.save(bike);
        }

        return true;
    }

    public Boolean freeBike(RentRequestMessage dto) {
        Bike bike =  repository.findByBikeQr(dto.getBikeQr());
        if (bike == null) {
            throw new RuntimeException("Not found " + dto.getBikeQr());
        } else {
            bike.setOccupied(false);
            bike.setOccupiedTill(null);
            repository.save(bike);
        }

        return true;
    }

}
