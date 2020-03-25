package com.shipping.analyser.repository;

import com.shipping.analyser.model.SellerEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface SellerRepository extends ReactiveMongoRepository<SellerEntity, String> {

    public Mono<SellerEntity> findById(String id);

    @Query("{ 'username': ?0 }")
    public Mono<SellerEntity> findByUserName(String username);

    public Mono<SellerEntity> save(SellerEntity seller);

    public Flux<SellerEntity> findAll();
}
