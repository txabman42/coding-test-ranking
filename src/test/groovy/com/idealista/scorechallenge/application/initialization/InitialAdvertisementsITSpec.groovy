package com.idealista.scorechallenge.application.initialization

import com.idealista.scorechallenge.domain.model.Typology
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@WebFluxTest
@ContextConfiguration(classes = InitialAdvertisements)
class InitialAdvertisementsITSpec extends Specification {

    @Autowired
    InitialAdvertisements initialAdvertisements

    def "when properties loaded initialAdvertisements should load advertisements from resource json file"() {
        expect:
            Typology.CHALET.name() == initialAdvertisements.advertisements[0].typology
            "Dummy desc" == initialAdvertisements.advertisements[0].description
    }
}
