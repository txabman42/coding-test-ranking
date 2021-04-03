package com.idealista.scorechallenge.domain.service

import com.idealista.scorechallenge.application.model.AdvertisementRequestDto
import com.idealista.scorechallenge.domain.configuration.AdvertisementConfigurationProperties
import com.idealista.scorechallenge.domain.model.Advertisement
import com.idealista.scorechallenge.domain.model.Building
import com.idealista.scorechallenge.domain.model.Typology
import com.idealista.scorechallenge.domain.repository.AdvertisementRepository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Shared
import spock.lang.Specification

class AdvertisementServiceSpec extends Specification {

    ScoreServiceFactory scoreServiceFactory = Mock(ScoreServiceFactory)
    AdvertisementRepository advertisementRepository = Mock(AdvertisementRepository)
    AdvertisementConfigurationProperties configurationProperties = Mock(AdvertisementConfigurationProperties)
    AdvertisementService advertisementService = new AdvertisementServiceImpl(scoreServiceFactory, advertisementRepository, configurationProperties)

    @Shared
    AdvertisementRequestDto advertisementRequestDto
    Advertisement advertisement

    def setup() {
        advertisementRequestDto = new AdvertisementRequestDto(Typology.CHALET, "Dummy desc", [], 1, 1)
        advertisement = new Advertisement()
        advertisement.description = advertisementRequestDto.description
        advertisement.building = new Building()
        advertisement.building.typology = advertisementRequestDto.typology
        advertisement.building.houseSize = advertisementRequestDto.houseSize
        advertisement.building.gardenSize = advertisementRequestDto.gardenSize
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

    def "calculateScores should add scores to each persisted advertisement with no score"() {
        given:
            Integer score = 50
            Integer limitScore = 40
            ScoreService scoreService = Mock(ScoreService)
            Advertisement advertisementWithScore = new Advertisement()
            advertisementWithScore.score = 1
            1 * advertisementRepository.findAll() >> Flux.fromIterable([advertisement, advertisementWithScore])
            1 * scoreServiceFactory.getStrategy(advertisement.building.typology) >> scoreService
            1 * scoreService.calculateScore(advertisement) >> score
            1 * configurationProperties.getIrrelevantScore() >> limitScore
            1 * advertisementRepository.save({
                Mono<Advertisement> a -> score == a.block().score && null == a.block().irrelevantSince
            }) >> Mono.just(advertisement)
        when:
            def result = advertisementService.calculateScores()
        then:
            StepVerifier.create(result).verifyComplete()
    }

    def "calculateScores should add irrelevant date is score is less than limit"() {
        given:
            Integer score = 30
            Integer limitScore = 40
            ScoreService scoreService = Mock(ScoreService)
            1 * advertisementRepository.findAll() >> Flux.fromIterable([advertisement])
            1 * scoreServiceFactory.getStrategy(advertisement.building.typology) >> scoreService
            1 * scoreService.calculateScore(advertisement) >> score
            1 * configurationProperties.getIrrelevantScore() >> limitScore
            1 * advertisementRepository.save({
                Mono<Advertisement> a -> score == a.block().score && null != a.block().irrelevantSince
            }) >> Mono.just(advertisement)
        when:
            def result = advertisementService.calculateScores()
        then:
            StepVerifier.create(result).verifyComplete()
    }

    def "findAllNoIrrelevant should return all no irrelevant advertisements"() {
        given:
            advertisement.setIrrelevantSince(new Date())
            Advertisement advertisementScore1 = new Advertisement()
            advertisementScore1.score = 100
            advertisementScore1.building = new Building()
            Advertisement advertisementScore2 = new Advertisement()
            advertisementScore2.score = 500
            advertisementScore2.building = new Building()
            1 * advertisementRepository.findAll() >> Flux.fromIterable([advertisement, advertisementScore1, advertisementScore2])
        when:
            def result = advertisementService.findAllNoIrrelevant()
        then:
            StepVerifier.create(result)
                    .expectNextMatches(advertisementDto -> advertisementDto.id == advertisementScore2.uuid)
                    .expectNextMatches(advertisementDto -> advertisementDto.id == advertisementScore1.uuid)
                    .verifyComplete()
    }
}
