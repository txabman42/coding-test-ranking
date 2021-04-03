package com.idealista.scorechallenge.domain.service

import com.idealista.scorechallenge.domain.configuration.KeyWords
import com.idealista.scorechallenge.domain.model.Advertisement
import com.idealista.scorechallenge.domain.model.Building
import com.idealista.scorechallenge.domain.model.Picture
import com.idealista.scorechallenge.domain.model.Typology
import spock.lang.Specification
import spock.lang.Unroll

class ScoreServiceChaletSpec extends Specification {

    KeyWords keyWords = Mock(KeyWords)
    ScoreServiceChalet scoreServiceChalet = new ScoreServiceChalet(keyWords)

    def "getScoreType should return chalet"() {
        expect:
            scoreServiceChalet.getScoreType() == Typology.CHALET
    }

    @Unroll
    def "given #wordNumber calculateWordNumberScore should return #expectedScore"() {
        expect:
            scoreServiceChalet.calculateWordNumberScore(wordNumber) == expectedScore
        where:
            wordNumber || expectedScore
            0          || 0
            50         || 0
            51         || 20
            100        || 20
    }

    @Unroll
    def "given description, pictures, houseSize and gardenSize isComplete should return if advertisement is complete"() {
        given:
            Advertisement advertisement = new Advertisement()
            advertisement.description = description
            advertisement.building = new Building(Typology.FLAT, pictures, houseSize, gardenSize)
        expect:
            scoreServiceChalet.isComplete(advertisement) == expectedIsComplete
        where:
            description | pictures        | houseSize | gardenSize || expectedIsComplete
            ""          | []              | null      | null       || false
            ""          | []              | 10        | null       || false
            ""          | [new Picture()] | null      | null       || false
            ""          | [new Picture()] | 10        | null       || false
            "a"         | []              | null      | null       || false
            "a"         | []              | 10        | null       || false
            "a"         | [new Picture()] | null      | null       || false
            "a"         | [new Picture()] | 10        | null       || false
            ""          | []              | null      | 10         || false
            ""          | []              | 10        | 10         || false
            ""          | [new Picture()] | null      | 10         || false
            ""          | [new Picture()] | 10        | 10         || false
            "a"         | []              | null      | 10         || false
            "a"         | []              | 10        | 10         || false
            "a"         | [new Picture()] | null      | 10         || false
            "a"         | [new Picture()] | 10        | 10         || true
    }
}
