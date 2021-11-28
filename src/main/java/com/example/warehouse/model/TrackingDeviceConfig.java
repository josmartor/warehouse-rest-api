package com.example.warehouse.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "ColTrackingDevicesConfigs")
public class TrackingDeviceConfig {

    @Id
    private String id;

    private Float temperature;

    private SimCard simCard;

}
