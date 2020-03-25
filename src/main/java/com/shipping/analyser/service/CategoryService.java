package com.shipping.analyser.service;

import com.shipping.analyser.dto.CategoryDto;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CategoryService {

    public Flux<CategoryDto> findAllCategory();

    public Flux<CategoryDto> findAllCategoryEnabled(boolean enabled);

    public Mono<?> saveCategory(CategoryDto categoryDto);

    public Mono<Boolean> isCategoryIDEnabled(int categoryId, boolean enabled);


}
