package com.idealista.scorechallenge.domain.service

import com.idealista.scorechallenge.domain.configuration.KeyWords
import com.idealista.scorechallenge.domain.model.Advertisement
import com.idealista.scorechallenge.domain.model.Building
import com.idealista.scorechallenge.domain.model.Picture
import com.idealista.scorechallenge.domain.model.Typology
import spock.lang.Specification
import spock.lang.Unroll

class ScoreServiceFlatSpec extends Specification {

    KeyWords keyWords = Mock(KeyWords)
    ScoreServiceFlat scoreServiceFlat = new ScoreServiceFlat(keyWords)

    def "getScoreType should return flat"() {
        expect:
            scoreServiceFlat.getScoreType() == Typology.FLAT
    }

    @Unroll
    def "given #wordNumber calculateWordNumberScore should return #expectedScore"() {
        expect:
            scoreServiceFlat.calculateWordNumberScore(wordNumber) == expectedScore
        where:
            wordNumber || expectedScore
            0          || 0
            29         || 0
            30         || 10
            50         || 20
            51         || 20
            100        || 20
    }

    @Unroll
    def "given description, pictures and houseSize isComplete should return if advertisement is complete"() {
        given:
            Advertisement advertisement = new Advertisement()
            advertisement.description = description
            advertisement.building = new Building(Typology.FLAT, pictures, houseSize, 0)
        expect:
            scoreServiceFlat.isComplete(advertisement) == expectedIsComplete
        where:
            description | pictures        | houseSize || expectedIsComplete
            ""          | []              | null      || false
            ""          | []              | 10        || false
            ""          | [new Picture()] | null      || false
            ""          | [new Picture()] | 10        || false
            "a"         | []              | null      || false
            "a"         | []              | 10        || false
            "a"         | [new Picture()] | null      || false
            "a"         | [new Picture()] | 10        || true
    }
}
