package org.nttdata.credits_service.repository;

import org.nttdata.credits_service.domain.entity.Credit;
import org.nttdata.credits_service.domain.type.CreditType;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public interface CreditRepository extends ReactiveCrudRepository<Credit, String> {
    Flux<Credit> findAllByOwnerId(String ownerId);

    Mono<Boolean> existsCreditsByOwnerIdAndCreditType(String ownerId, CreditType creditType);
}
