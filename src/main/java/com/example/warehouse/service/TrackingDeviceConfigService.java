package com.example.warehouse.service;

import com.example.warehouse.model.SimStatus;
import com.example.warehouse.model.TrackingDeviceConfig;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface TrackingDeviceConfigService {

    Mono<TrackingDeviceConfig> save(TrackingDeviceConfig rackingDeviceConfig);

    Mono<TrackingDeviceConfig> update(String id, TrackingDeviceConfig rackingDeviceConfig);

    Mono<TrackingDeviceConfig> delete(String id);

    Mono<TrackingDeviceConfig> findById(String threadId);

    Flux<TrackingDeviceConfig> findAll();

    Flux<TrackingDeviceConfig> findBySimCardStatus(SimStatus simStatus);

    Flux<TrackingDeviceConfig> findByTemperatureBetweenAndSimCardStatusOrderById(Float temperatureGT, Float temperatureLT, SimStatus status);

}
