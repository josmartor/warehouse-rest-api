package com.example.warehouse.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SimStatus {

    ACTIVE,
    WAITING_FOR_ACTIVATION,
    BLOCKED,
    DEACTIVATED

}
