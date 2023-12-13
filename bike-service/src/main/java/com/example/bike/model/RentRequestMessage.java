package com.example.bike.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentRequestMessage {
    public enum Type {RENT, UNRENT, CHECK};

    private String message;
    private Type command;
    private String workflowId;
    private Region region;
    private String bikeQr;
    private Long howLong;
}
