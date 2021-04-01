package com.idealista.scorechallenge.infrastructure.inmemory

import com.idealista.scorechallenge.domain.model.BaseEntity
import com.idealista.scorechallenge.domain.repository.BaseReactiveCrudRepository
import com.idealista.scorechallenge.infrastructure.inmemory.database.BaseDatabase
import com.idealista.scorechallenge.infrastructure.inmemory.repository.BaseReactiveInMemoryRepository
import reactor.core.publisher.Mono
import reactor.test.StepVerifier
import spock.lang.Shared
import spock.lang.Specification

class BaseReactiveInMemoryRepositorySpec extends Specification {

    class DummyEntity extends BaseEntity {
        DummyEntity() {}
    }

    class DummyReactiveInMemoryRepository extends BaseReactiveInMemoryRepository<DummyEntity, UUID> {
        DummyReactiveInMemoryRepository(BaseDatabase<DummyEntity, UUID> baseDatabase) {
            super(baseDatabase)
        }
    }

    BaseDatabase<DummyEntity, UUID> database = Mock(BaseDatabase)

    BaseReactiveCrudRepository baseReactiveCrudRepository = new DummyReactiveInMemoryRepository(database)

    @Shared
    DummyEntity dummyEntity1, dummyEntity2

    def setup() {
        dummyEntity1 = new DummyEntity()
        dummyEntity2 = new DummyEntity()
    }

    def "given valid entity save should store it correctly"() {
        given:
            Mono<DummyEntity> monoDummyEntity = Mono.just(dummyEntity1)
            1 * database.getDb() >> [:]
        when:
            def result = baseReactiveCrudRepository.save(monoDummyEntity)
        then:
            StepVerifier.create(result).expectNext(dummyEntity1).verifyComplete()
    }
}
