package com.idealista.scorechallenge.application.model

import com.idealista.scorechallenge.domain.model.Typology
import spock.lang.Specification
import spock.lang.Unroll

class AdvertisementRequestDtoSpec extends Specification {

    @Unroll
    def "given #typology typology and pictures isNotValid should return #expectedResponse"() {
        given:
            AdvertisementRequestDto advertisementRequestDto = new AdvertisementRequestDto(typology, null, pictures, null, null)
        expect:
            advertisementRequestDto.isNotValid() == expectedResponse
        where:
            typology        | pictures                     || expectedResponse
            null            | null                         || true
            Typology.FLAT   | null                         || true
            Typology.CHALET | null                         || true
            Typology.GARAGE | null                         || true
            null            | []                           || true
            Typology.FLAT   | []                           || false
            Typology.CHALET | [new PictureDto(null, null)] || false
            Typology.GARAGE | [new PictureDto(null, null)] || false
            Typology.CHALET | [new PictureDto(null, null)] || false
    }
}
