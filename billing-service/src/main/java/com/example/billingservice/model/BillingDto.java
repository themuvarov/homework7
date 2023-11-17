package com.example.billingservice.model;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Value.Immutable
@JsonDeserialize(builder = ImmutableBillingDto.Builder.class)
public abstract class BillingDto {
    public abstract Integer getSum();
    public abstract Integer getTariff();
    public abstract String getAgent();
    public abstract Long getTimestamp();
}