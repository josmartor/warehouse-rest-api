package com.example.warehouse.unitary

import com.example.warehouse.model.SimCard
import com.example.warehouse.model.SimStatus
import com.example.warehouse.model.TrackingDeviceConfig
import com.example.warehouse.repository.TrackingDeviceConfigRepository
import com.example.warehouse.service.TrackingDeviceConfigService
import com.example.warehouse.service.impl.TrackingDeviceConfigServiceImpl
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Specification

class TrackingDeviceConfigServiceImplSpec extends Specification {


    TrackingDeviceConfigRepository trackingDeviceConfigRepository = Mock()

    TrackingDeviceConfigService trackingDeviceConfigService = new TrackingDeviceConfigServiceImpl(trackingDeviceConfigRepository)

    def 'Save a new tracking device config'() {
        given:
        def trackingDeviceConfig = TrackingDeviceConfig.builder().id("id1").temperature(30.0).
                simCard(SimCard.builder().simId("1").operatorCode('asdf84l').country('EN').status(SimStatus.WAITING_FOR_ACTIVATION).build()).build()
        trackingDeviceConfigRepository.save(_) >> Mono.just(trackingDeviceConfig)

        when:
        def result = trackingDeviceConfigService.save(trackingDeviceConfig)

        then:
        result != null
        StepVerifier.create(result).expectNext(trackingDeviceConfig).verifyComplete()
    }

    def 'Update a tracking device config'() {
        given:
        def oldTrackingDeviceConfig = TrackingDeviceConfig.builder().id("id1").temperature(30.0).
                simCard(SimCard.builder().simId("1").operatorCode('asdf84l').country('EN').status(SimStatus.WAITING_FOR_ACTIVATION).build()).build()
        def newTrackingDeviceConfig = TrackingDeviceConfig.builder().id("id1").temperature(30.0).
                simCard(SimCard.builder().simId("1").operatorCode('asdf84l').country('EN').status(SimStatus.ACTIVE).build()).build()
        trackingDeviceConfigRepository.findById(_) >> Mono.just(oldTrackingDeviceConfig)
        trackingDeviceConfigRepository.save(_) >> Mono.just(newTrackingDeviceConfig)

        when:
        def result = trackingDeviceConfigService.update(newTrackingDeviceConfig.id, newTrackingDeviceConfig)

        then:
        result != null
        StepVerifier.create(result).expectNext(newTrackingDeviceConfig).verifyComplete()
    }

    def 'Delete a tracking device config'() {
        given:
        def trackingDeviceConfig = TrackingDeviceConfig.builder().id("id1").temperature(30.0).
                simCard(SimCard.builder().simId("1").operatorCode('asdf84l').country('EN').status(SimStatus.WAITING_FOR_ACTIVATION).build()).build()
        trackingDeviceConfigRepository.findById(_) >> Mono.just(trackingDeviceConfig)
        trackingDeviceConfigRepository.deleteById(_) >> Mono.just(trackingDeviceConfig)

        when:
        def result = trackingDeviceConfigService.delete(trackingDeviceConfig.id)

        then:
        result != null
        StepVerifier.create(result).expectNext(trackingDeviceConfig).verifyComplete()
    }

    def 'Find a tracking device config by id'() {
        given:
        def trackingDeviceConfig = TrackingDeviceConfig.builder().id("id1").temperature(30.0).
                simCard(SimCard.builder().simId("1").operatorCode('asdf84l').country('EN').status(SimStatus.WAITING_FOR_ACTIVATION).build()).build()
        trackingDeviceConfigRepository.findById(_) >> Mono.just(trackingDeviceConfig)

        when:
        def result = trackingDeviceConfigService.findById(trackingDeviceConfig.id)

        then:
        result != null
        StepVerifier.create(result).expectNext(trackingDeviceConfig).verifyComplete()
    }

    def 'Find all tracking devices config'() {
        given:
        def trackingDeviceConfig1 = TrackingDeviceConfig.builder().id("id1").temperature(30.0).
                simCard(SimCard.builder().simId("1").operatorCode('asdf84l').country('EN').status(SimStatus.WAITING_FOR_ACTIVATION).build()).build()
        def trackingDeviceConfig2 = TrackingDeviceConfig.builder().id("id2").temperature(30.0).
                simCard(SimCard.builder().simId("2").operatorCode('asdf84l').country('EN').status(SimStatus.WAITING_FOR_ACTIVATION).build()).build()
        trackingDeviceConfigRepository.findAll() >> Flux.just(trackingDeviceConfig1, trackingDeviceConfig2)

        when:
        def result = trackingDeviceConfigService.findAll()

        then:
        result != null
        StepVerifier.create(result).expectNext(trackingDeviceConfig1).expectNext(trackingDeviceConfig2).verifyComplete()
    }

    def 'Find all tracking devices ready for sale'() {
        given:
        def trackingDeviceConfig1 = TrackingDeviceConfig.builder().id("id1").temperature(75.0).
                simCard(SimCard.builder().simId("1").operatorCode('asdf84l').country('EN').status(SimStatus.ACTIVE).build()).build()
        def trackingDeviceConfig2 = TrackingDeviceConfig.builder().id("id2").temperature(85.0).
                simCard(SimCard.builder().simId("2").operatorCode('asdf84l').country('EN').status(SimStatus.ACTIVE).build()).build()
        def trackingDeviceConfig3 = TrackingDeviceConfig.builder().id("id3").temperature(-25.0).
                simCard(SimCard.builder().simId("3").operatorCode('asdf84l').country('EN').status(SimStatus.WAITING_FOR_ACTIVATION).build()).build()
        trackingDeviceConfigRepository.findByTemperatureBetweenAndSimCardStatusOrderById(_, _, _) >> Flux.just(trackingDeviceConfig1, trackingDeviceConfig2, trackingDeviceConfig3)

        when:
        def result = trackingDeviceConfigService.findByTemperatureBetweenAndSimCardStatusOrderById(-25, 85, SimStatus.ACTIVE)

        then:
        result != null
        StepVerifier.create(result).expectNext(trackingDeviceConfig1).expectNext(trackingDeviceConfig2).expectNext(trackingDeviceConfig3).verifyComplete()
    }

    def 'Find all tracking devices waiting for activation'() {
        given:
        def trackingDeviceConfig1 = TrackingDeviceConfig.builder().id("id1").temperature(85.0).
                simCard(SimCard.builder().simId("1").operatorCode('asdf84l').country('EN').status(SimStatus.WAITING_FOR_ACTIVATION).build()).build()
        def trackingDeviceConfig2 = TrackingDeviceConfig.builder().id("id2").temperature(-25.0).
                simCard(SimCard.builder().simId("2").operatorCode('asdf84l').country('EN').status(SimStatus.WAITING_FOR_ACTIVATION).build()).build()
        trackingDeviceConfigRepository.findBySimCardStatus(_) >> Flux.just(trackingDeviceConfig1, trackingDeviceConfig2)

        when:
        def result = trackingDeviceConfigService.findBySimCardStatus(SimStatus.ACTIVE)

        then:
        result != null
        StepVerifier.create(result).expectNext(trackingDeviceConfig1).expectNext(trackingDeviceConfig2).verifyComplete()
    }

}
