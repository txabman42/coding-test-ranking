package com.idealista.scorechallenge

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ScoreChallengeApplicationITSpec extends Specification {

    def "load context"() {
        expect:
            true
    }
}
