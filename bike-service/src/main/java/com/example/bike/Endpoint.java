package com.example.bike;

import com.example.bike.model.BikeDto;
import com.example.bike.model.Region;
import com.example.bike.service.BikeService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/bike/v1")
@RequiredArgsConstructor
public class Endpoint {

    private final BikeService bikeService;

    private static final Logger LOG = LogManager.getLogger();

    @GetMapping(value = "/{region}")
    ResponseEntity<List<String>> allBikesInRegion(@PathVariable Region region) {
        LOG.info("Bikes in region {}", region);
        List<String> result = bikeService.getAllBikes(region);
        return ResponseEntity.ok(result);
    }

    @GetMapping(value = "/free/{region}")
    ResponseEntity<List<String>> freeBikesInRegion(@PathVariable Region region) {
        LOG.info("Free bikes in region {}", region);
        List<String> result = bikeService.getAllBikesOccupied(region, false);
        return ResponseEntity.ok(result);
    }

    @PostMapping(value = "/add",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    ResponseEntity<Long> add(@RequestBody BikeDto dto) {
        LOG.info("Add bike to region {} {}", dto.getBikeQr(), dto.getRegion());
        Long account = bikeService.addBike(dto);
        return ResponseEntity.ok(account);
    }

}
