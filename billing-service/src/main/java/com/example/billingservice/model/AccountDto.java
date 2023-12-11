package com.example.billingservice.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.immutables.value.Value;

import java.math.BigDecimal;

@Value.Immutable
@JsonDeserialize(builder = ImmutableAccountDto.Builder.class)
public abstract class AccountDto {
    public abstract String getAgent();

}