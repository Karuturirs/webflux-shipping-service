package com.shipping.analyser.repository;

import com.shipping.analyser.model.CategoryEntity;
import com.shipping.analyser.model.SellerEntity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryRepository extends ReactiveMongoRepository<CategoryEntity, String> {

    public Flux<CategoryEntity> findAll();

    public Mono<CategoryEntity> save(CategoryEntity categoryEntity);

    @Query("{ 'enabled': ?0 }")
    public Flux<CategoryEntity> findByEnabled(boolean enabled);

    @Query("{ 'categoryName': ?0 }")
    public Mono<CategoryEntity> findByCategoryName(String categoryName);

    @Query("{ 'categoryId': ?0 }")
    public Mono<CategoryEntity> findByCategoryId(int categoryId);

    @Query("{ 'categoryId': ?0, 'enabled': ?1 }")
    public Mono<CategoryEntity> findByCategoryIdAndEnabled(int categoryId, boolean enabled);

    @Query("{ 'categoryId': ?0 , 'categoryName': ?1 }")
    public Mono<CategoryEntity> findByCategoryIdAndName(int categoryId , String categoryName);

}
