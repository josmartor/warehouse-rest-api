package com.example.warehouse.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SimCard {

    private String simId;

    private String operatorCode;

    private String country;

    private SimStatus status;


}
