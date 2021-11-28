package com.example.warehouse.integration


import groovyx.net.http.RESTClient
import spock.lang.Specification

class WarehouseIT extends Specification {

    RESTClient restClient = new RESTClient("http://localhost:8081/tracking-devices/")

    def "Check saving a new tracking device config"() {

        given :
        def requestBody = [id: 'id11', temperature: '-30', simCard: [simId: '1', operatorCode: 'eier438-fs', country: 'EN', status: 'ACTIVE']]

        when:
        def response = restClient.post(path: '', body: requestBody, requestContentType: 'application/json')

        then:
        response.status == 201

        and: "Body contains proper values"
        def newTrackingDeviceConfig = response.data
        assert newTrackingDeviceConfig.id == "id11"
        assert newTrackingDeviceConfig.temperature == -30.0
        assert newTrackingDeviceConfig.simCard != null
        assert newTrackingDeviceConfig.simCard.simId == '1'
        assert newTrackingDeviceConfig.simCard.operatorCode == 'eier438-fs'
        assert newTrackingDeviceConfig.simCard.country == 'EN'
        assert newTrackingDeviceConfig.simCard.status == 'ACTIVE'

        cleanup:
        deleteTestTrackingDeviceConfig(newTrackingDeviceConfig.id)
    }

    def 'Check getting a tracking device config by id'(){
        setup:
        def testTrackingDeviceConfigId = createTestTrackingDeviceConfig().data.id

        when:
        def response = restClient.get( path: testTrackingDeviceConfigId)

        then:
        response.status == 200

        and:
        def foundTrackingDeviceConfig = response.data
        assert foundTrackingDeviceConfig.id == "id11"
        assert foundTrackingDeviceConfig.temperature == -30.0
        assert foundTrackingDeviceConfig.simCard != null
        assert foundTrackingDeviceConfig.simCard.simId == '1'
        assert foundTrackingDeviceConfig.simCard.operatorCode == 'eier438-fs'
        assert foundTrackingDeviceConfig.simCard.country == 'EN'
        assert foundTrackingDeviceConfig.simCard.status == 'ACTIVE'

        cleanup:
        deleteTestTrackingDeviceConfig(testTrackingDeviceConfigId)
    }


    def 'Check updating a tracking device config'(){
        setup:
        def testTrackingDeviceConfigId = createTestTrackingDeviceConfig().data.id

        when:
        def updatedTrackingDeviceConfig = [id: 'id11', temperature: '-30', simCard: [simId: '1', operatorCode: 'eier438-fs', country: 'EN', status: 'WAITING_FOR_ACTIVATION']]
        def response = restClient.put(path: testTrackingDeviceConfigId, body: updatedTrackingDeviceConfig, requestContentType: 'application/json')

        then:
        response.status == 200

        and:
        assert response.data.id == "id11"
        assert response.data.temperature == -30.0
        assert response.data.simCard != null
        assert response.data.simCard.simId == '1'
        assert response.data.simCard.operatorCode == 'eier438-fs'
        assert response.data.simCard.country == 'EN'
        assert response.data.simCard.status == 'WAITING_FOR_ACTIVATION'

        cleanup:
        deleteTestTrackingDeviceConfig(testTrackingDeviceConfigId)
    }

    def 'Check deleting a tracking device config'(){
        setup:
        def testTrackingDeviceConfigId = createTestTrackingDeviceConfig().data.id

        when:
        def response = restClient.delete(path: testTrackingDeviceConfigId)

        then:
        response.status == 200

    }

    def 'Check retrieving all tracking devices ready for sale'(){

        when:
        def response = restClient.get(path: 'ready-for-sale')

        then:
        response.status == 200

    }

    def 'Check retrieving all tracking devices waiting for activation'(){

        when:
        def response = restClient.get(path: 'waiting-activation')

        then:
        response.status == 200

    }

    def createTestTrackingDeviceConfig() {
        def requestBody = [id: 'id11', temperature: '-30', simCard: [simId: '1', operatorCode: 'eier438-fs', country: 'EN', status: 'ACTIVE']]
        return restClient.post(path: '', body: requestBody, requestContentType: 'application/json')
    }

    def deleteTestTrackingDeviceConfig(def trackingDeviceConfigId) {
        return restClient.delete(path: trackingDeviceConfigId)
    }
}


