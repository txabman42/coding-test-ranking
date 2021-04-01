package com.idealista.scorechallenge.domain.service

import com.idealista.scorechallenge.application.model.AdvertisementRequestDto
import com.idealista.scorechallenge.domain.model.Advertisement
import com.idealista.scorechallenge.domain.model.Typology
import com.idealista.scorechallenge.domain.repository.AdvertisementRepository
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Shared
import spock.lang.Specification

class AdvertisementServiceSpec extends Specification {

    AdvertisementRepository advertisementRepository = Mock(AdvertisementRepository)
    AdvertisementService advertisementService = new AdvertisementServiceImpl(advertisementRepository)

    @Shared
    AdvertisementRequestDto advertisementRequestDto
    Advertisement advertisement

    def setup() {
        advertisementRequestDto = new AdvertisementRequestDto(Typology.CHALET, "Dummy desc", [], 1, 1)
        advertisement = new Advertisement()
        advertisement.typology = advertisementRequestDto.typology
        advertisement.description = advertisementRequestDto.description
        advertisement.houseSize = advertisementRequestDto.houseSize
        advertisement.gardenSize = advertisementRequestDto.gardenSize
    }

    def "given valid advertisementRequestDto create should save it correctly"() {
        given:
            1 * advertisementRepository.save({
                Mono<Advertisement> monoA -> advertisement == monoA.block()
            }) >> Mono.empty()
        when:
            def result = advertisementService.create(Mono.just(advertisementRequestDto))
        then:
            StepVerifier.create(result).verifyComplete()
            noExceptionThrown()
    }
}
