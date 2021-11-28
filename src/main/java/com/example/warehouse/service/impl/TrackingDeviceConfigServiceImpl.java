package com.example.warehouse.service.impl;

import com.example.warehouse.model.SimStatus;
import com.example.warehouse.model.TrackingDeviceConfig;
import com.example.warehouse.repository.TrackingDeviceConfigRepository;
import com.example.warehouse.service.TrackingDeviceConfigService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TrackingDeviceConfigServiceImpl implements TrackingDeviceConfigService {

    private final TrackingDeviceConfigRepository trackingDeviceConfigRepository;

    public Mono<TrackingDeviceConfig> save(TrackingDeviceConfig trackingDeviceConfig) {
        return trackingDeviceConfigRepository.save(trackingDeviceConfig);
    }

    public Mono<TrackingDeviceConfig> delete(String id) {
        return trackingDeviceConfigRepository
                .findById(id)
                .flatMap(p -> trackingDeviceConfigRepository.deleteById(p.getId()).thenReturn(p));

    }

    public Mono<TrackingDeviceConfig> update(String id, TrackingDeviceConfig trackingDeviceConfig) {
        return trackingDeviceConfigRepository.findById(id)
                .flatMap(trackingDeviceConfig1 -> {
                    trackingDeviceConfig.setId(id);
                    return save(trackingDeviceConfig);
                })
                .switchIfEmpty(Mono.empty());
    }

    public Mono<TrackingDeviceConfig> findById(String id) {
        return trackingDeviceConfigRepository.findById(id);
    }

    public Flux<TrackingDeviceConfig> findAll() {
        return trackingDeviceConfigRepository.findAll();
    }

    public Flux<TrackingDeviceConfig> findBySimCardStatus(SimStatus simStatus) {
        return trackingDeviceConfigRepository.findBySimCardStatus(simStatus);
    }

    public Flux<TrackingDeviceConfig> findByTemperatureBetweenAndSimCardStatusOrderById(Float temperatureGT, Float temperatureLT, SimStatus status) {
        return trackingDeviceConfigRepository.findByTemperatureBetweenAndSimCardStatusOrderById(temperatureGT, temperatureLT, status);
    }

}
