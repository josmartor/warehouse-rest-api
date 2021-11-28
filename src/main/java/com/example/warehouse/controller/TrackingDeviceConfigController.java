package com.example.warehouse.controller;

import com.example.warehouse.model.SimStatus;
import com.example.warehouse.model.TrackingDeviceConfig;
import com.example.warehouse.service.TrackingDeviceConfigService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("tracking-devices")
@AllArgsConstructor
public class TrackingDeviceConfigController {

    @Autowired
    private final TrackingDeviceConfigService trackingDeviceConfigService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    private Mono<TrackingDeviceConfig> save(@RequestBody TrackingDeviceConfig trackingDeviceConfig) {
        return trackingDeviceConfigService.save(trackingDeviceConfig);
    }

    @DeleteMapping("{id}")
    private Mono<ResponseEntity<TrackingDeviceConfig>> delete(@PathVariable("id") String id) {
        return trackingDeviceConfigService.delete(id)
                .flatMap(trackingDeviceConfig -> Mono.just(ResponseEntity.ok(trackingDeviceConfig)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @PutMapping("/{id}")
    private Mono<ResponseEntity<TrackingDeviceConfig>> update(@PathVariable("id") String id, @RequestBody TrackingDeviceConfig trackingDeviceConfig) {
        return trackingDeviceConfigService.update(id, trackingDeviceConfig)
                .flatMap(trackingDeviceConfig1 -> Mono.just(ResponseEntity.ok(trackingDeviceConfig1)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));
    }

    @GetMapping("/{id}")
    private Mono<TrackingDeviceConfig> findById(@PathVariable("id") String id) {
        return trackingDeviceConfigService.findById(id);
    }

    @GetMapping(value = "")
    private Flux<TrackingDeviceConfig> findAll() {
        return trackingDeviceConfigService.findAll();
    }

    @RequestMapping(value = "/waiting-activation", method = RequestMethod.GET)
    public Flux<TrackingDeviceConfig> trackingDevicesAwaitingActivation() {
        return trackingDeviceConfigService.findBySimCardStatus(SimStatus.WAITING_FOR_ACTIVATION);
    }

    @RequestMapping(value = "/ready-for-sale", method = RequestMethod.GET)
    public Flux<TrackingDeviceConfig> trackingDevicesReadyForSale() {
        return trackingDeviceConfigService.findByTemperatureBetweenAndSimCardStatusOrderById(-25f, 85f, SimStatus.ACTIVE);
    }

}
