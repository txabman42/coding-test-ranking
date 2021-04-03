package com.idealista.scorechallenge.domain.service

import com.idealista.scorechallenge.domain.configuration.KeyWords
import com.idealista.scorechallenge.domain.model.Advertisement
import com.idealista.scorechallenge.domain.model.Building
import com.idealista.scorechallenge.domain.model.Picture
import com.idealista.scorechallenge.domain.model.Typology
import spock.lang.Specification
import spock.lang.Unroll

class ScoreServiceGarageSpec extends Specification {

    KeyWords keyWords = Mock(KeyWords)
    ScoreServiceGarage scoreServiceGarage = new ScoreServiceGarage(keyWords)

    def "getScoreType should return garage"() {
        expect:
            scoreServiceGarage.getScoreType() == Typology.GARAGE
    }

    @Unroll
    def "given #wordNumber calculateWordNumberScore should return 0"() {
        expect:
            scoreServiceGarage.calculateWordNumberScore(wordNumber) == 0
        where:
            wordNumber || _
            0          || _
            50         || _
            51         || _
            100        || _
    }

    @Unroll
    def "given pictures isComplete should return if advertisement is complete"() {
        given:
            Advertisement advertisement = new Advertisement()
            advertisement.description = null
            advertisement.building = new Building(Typology.GARAGE, pictures, null, null)
        expect:
            scoreServiceGarage.isComplete(advertisement) == expectedIsComplete
        where:
            pictures                       || expectedIsComplete
            []                             || false
            [new Picture()]                || true
            [new Picture(), new Picture()] || true
    }
}
