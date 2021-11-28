package com.example.warehouse.repository;

import com.example.warehouse.model.SimStatus;
import com.example.warehouse.model.TrackingDeviceConfig;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface TrackingDeviceConfigRepository extends ReactiveMongoRepository<TrackingDeviceConfig, String> {

    Flux<TrackingDeviceConfig> findBySimCardStatus(SimStatus status);

    Flux<TrackingDeviceConfig> findByTemperatureBetweenAndSimCardStatusOrderById(Float temperatureGT, Float temperatureLT, SimStatus status);

}
