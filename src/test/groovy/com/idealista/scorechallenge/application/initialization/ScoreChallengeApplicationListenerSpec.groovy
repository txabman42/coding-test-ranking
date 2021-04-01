package com.idealista.scorechallenge.application.initialization

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.idealista.scorechallenge.application.model.AdvertisementRequestDto
import com.idealista.scorechallenge.domain.model.Typology
import com.idealista.scorechallenge.domain.service.AdvertisementService
import org.springframework.boot.context.event.ApplicationReadyEvent
import reactor.core.publisher.Mono
import spock.lang.Shared
import spock.lang.Specification

class ScoreChallengeApplicationListenerSpec extends Specification {

    ObjectMapper objectMapper = Mock(ObjectMapper)
    InitialAdvertisements initialAdvertisements = Mock(InitialAdvertisements)
    AdvertisementService advertisementService = Mock(AdvertisementService)
    ScoreChallengeApplicationListener scoreChallengeApplicationListener =
            new ScoreChallengeApplicationListener(objectMapper, initialAdvertisements, advertisementService)

    @Shared
    ApplicationReadyEvent applicationReadyEvent
    AdvertisementRequestDto advertisementRequestDto1

    def setup() {
        applicationReadyEvent = Mock(ApplicationReadyEvent)
        advertisementRequestDto1 = new AdvertisementRequestDto(Typology.CHALET, "Dummy desc 1", null, 1, 1)
    }

    def "given random applicationReadyEvent and unique advertisementRequest onApplicationEvent should create all initial advertisements"() {
        given:
            def linkedAdvertisements = [["dummy": "random"]]
            1 * initialAdvertisements.getAdvertisements() >> linkedAdvertisements
            1 * objectMapper.convertValue(linkedAdvertisements, new TypeReference<List<AdvertisementRequestDto>>() {
            }) >> [advertisementRequestDto1]
            1 * advertisementService.create({ Mono<AdvertisementRequestDto> a -> advertisementRequestDto1 == a.block() }) >> Mono.empty()
        when:
            scoreChallengeApplicationListener.onApplicationEvent(applicationReadyEvent)
        then:
            noExceptionThrown()
    }

    def "given random applicationReadyEvent and multiple advertisementRequest onApplicationEvent should create all initial advertisements"() {
        given:
            AdvertisementRequestDto advertisementRequestDto2 = new AdvertisementRequestDto(Typology.CHALET, "Dummy desc 2", null, 2, 2)
            def linkedAdvertisements = [["dummy1": "random1"], ["dummy2": "random2"]]
            1 * initialAdvertisements.getAdvertisements() >> linkedAdvertisements
            1 * objectMapper.convertValue(linkedAdvertisements, new TypeReference<List<AdvertisementRequestDto>>() {}) >> [advertisementRequestDto1, advertisementRequestDto2]
            1 * advertisementService.create({ Mono<AdvertisementRequestDto> a -> advertisementRequestDto1 == a.block() }) >> Mono.empty()
            1 * advertisementService.create({ Mono<AdvertisementRequestDto> a -> advertisementRequestDto2 == a.block() }) >> Mono.empty()
        when:
            scoreChallengeApplicationListener.onApplicationEvent(applicationReadyEvent)
        then:
            noExceptionThrown()
    }

}
