package com.example.tarifservice;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Value.Immutable
@JsonDeserialize(builder = ImmutableTariff.Builder.class)
public abstract class Outward {
    public abstract String getAgent();
    public abstract Integer getTariff();
}
