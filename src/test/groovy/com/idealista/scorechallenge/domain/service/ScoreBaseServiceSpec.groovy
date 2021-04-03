package com.idealista.scorechallenge.domain.service

import com.idealista.scorechallenge.domain.configuration.KeyWords
import com.idealista.scorechallenge.domain.model.*
import spock.lang.Specification
import spock.lang.Unroll

class ScoreBaseServiceSpec extends Specification {

    KeyWords keyWords = Mock(KeyWords)
    DummyScoreBaseService dummyScoreBaseService = new DummyScoreBaseService(keyWords)

    def "getType should return scoreType"() {
        when:
            def result = dummyScoreBaseService.getType()
        then:
            result == Typology.FLAT
    }

    @Unroll
    def "given valid advertisement calculateScores should return score"() {
        given:
            Advertisement advertisement = new Advertisement()
            advertisement.description = description
            advertisement.building = new Building(Typology.FLAT, pictures, null, null)
            keyWords.getWords() >> ["a", "b"]
            dummyScoreBaseService.calculateScore(_ as Advertisement) >> 0
        when:
            def result = dummyScoreBaseService.calculateScore(advertisement)
        then:
            result == expectedScore
        where:
            description | pictures                                                         || expectedScore
            ""          | []                                                               || -10
            "a"         | []                                                               || 0
            "a c"       | []                                                               || 0
            "a b"       | []                                                               || 5
            ""          | [new Picture("url", Quality.HD)]                                 || 20
            ""          | [new Picture("url", Quality.SD)]                                 || 10
            ""          | [new Picture("url", Quality.SD), new Picture("url", Quality.SD)] || 20
            ""          | [new Picture("url", Quality.SD), new Picture("url", Quality.HD)] || 30
            ""          | [new Picture("url", Quality.HD), new Picture("url", Quality.HD)] || 40
            "a"         | [new Picture("url", Quality.SD), new Picture("url", Quality.HD)] || 40
            "a c"       | [new Picture("url", Quality.HD), new Picture("url", Quality.HD)] || 50
    }

    class DummyScoreBaseService extends ScoreBaseService {

        DummyScoreBaseService(KeyWords keyWords) {
            super(keyWords)
        }

        @Override
        protected Typology getScoreType() {
            return Typology.FLAT
        }

        @Override
        protected Integer calculateWordNumberScore(Integer wordNumber) {
            return 0
        }

        @Override
        protected boolean isComplete(Advertisement advertisement) {
            return false
        }
    }
}
