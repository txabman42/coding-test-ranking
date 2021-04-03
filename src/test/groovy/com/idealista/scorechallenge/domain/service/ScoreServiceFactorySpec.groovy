package com.idealista.scorechallenge.domain.service

import com.idealista.scorechallenge.domain.configuration.KeyWords
import com.idealista.scorechallenge.domain.model.Typology
import spock.lang.Specification

class ScoreServiceFactorySpec extends Specification {

    KeyWords keyWords = Mock(KeyWords)
    ScoreServiceFlat scoreServiceFlat = new ScoreServiceFlat(keyWords)
    ScoreServiceChalet scoreServiceChalet = new ScoreServiceChalet(keyWords)
    ScoreServiceGarage scoreServiceGarage = new ScoreServiceGarage(keyWords)

    ScoreServiceFactory scoreServiceFactory = new ScoreServiceFactory([scoreServiceFlat, scoreServiceChalet, scoreServiceGarage])

    def "given flat getStrategy should return ScoreServiceFlat"() {
        when:
            def result = scoreServiceFactory.getStrategy(Typology.FLAT)
        then:
            result instanceof ScoreServiceFlat
    }

    def "given chalet getStrategy should return ScoreServiceChalet"() {
        when:
            def result = scoreServiceFactory.getStrategy(Typology.CHALET)
        then:
            result instanceof ScoreServiceChalet
    }

    def "given garage getStrategy should return ScoreServiceGarage"() {
        when:
            def result = scoreServiceFactory.getStrategy(Typology.GARAGE)
        then:
            result instanceof ScoreServiceGarage
    }

    def "given null getStrategy should throw exception"() {
        when:
            scoreServiceFactory.getStrategy(null)
        then:
            thrown(IllegalArgumentException)
    }
}
