package com.example.bike.repository;

import com.example.bike.model.Bike;
import com.example.bike.model.Region;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface BikeRepository extends JpaRepository<Bike, Long> {

    Bike findByBikeQr(String qr);

    @Modifying
    @Query("update Bike b set b.occupied = true where b.bikeQr = ?1")
    void hold(String bikeQr);

    @Modifying
    @Query("update Bike b set b.occupied = false where b.bikeQr = ?1")
    void release(String bikeQr);

    List<Bike> findByRegion(Region region);

    List<Bike> findByRegionAndOccupied(Region region, boolean occupied);

}