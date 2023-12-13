package com.example.bike.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(builder = ImmutableBikeDto.Builder.class)
public abstract class BikeDto {
    public abstract Region getRegion();

    public abstract String getBikeQr();


}