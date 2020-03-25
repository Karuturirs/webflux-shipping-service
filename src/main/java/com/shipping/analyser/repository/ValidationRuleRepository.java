package com.shipping.analyser.repository;

import com.shipping.analyser.model.SellerEntity;
import com.shipping.analyser.model.ValidationRulesEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ValidationRuleRepository extends ReactiveMongoRepository<ValidationRulesEntity, String> {

    public Flux<ValidationRulesEntity> findAll();

    @Query("{ 'enabled': ?0 }")
    public Flux<ValidationRulesEntity> findByEnabled(boolean enabled);

    public Mono<ValidationRulesEntity> save(ValidationRulesEntity validationRulesEntity);

    @Query("{ 'type': ?0 , 'variable': ?1 }")
    public Mono<ValidationRulesEntity> findByTypeAndVariable(String type , String variable);

}
