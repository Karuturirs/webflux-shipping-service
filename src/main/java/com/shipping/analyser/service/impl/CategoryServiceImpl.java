package com.shipping.analyser.service.impl;

import com.shipping.analyser.dto.CategoryDto;
import com.shipping.analyser.model.CategoryEntity;
import com.shipping.analyser.repository.CategoryRepository;
import com.shipping.analyser.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Flux<CategoryDto> findAllCategory(){
        Flux<CategoryEntity> fl= categoryRepository.findAll();
        return fl.map(x -> new CategoryDto(x.getCategoryId(), x.getCategoryName(),x.isEnabled(), x.getEnrolledDate(), x.getModifiedDate()));
    }

    @Override
    public Flux<CategoryDto> findAllCategoryEnabled(boolean enabled){
        Flux<CategoryEntity> fl= categoryRepository.findByEnabled(enabled);
        return fl.map(x -> new CategoryDto(x.getCategoryId(), x.getCategoryName(),x.isEnabled(), x.getEnrolledDate(), x.getModifiedDate()));
    }


    @Override
    public Mono<?>  saveCategory(CategoryDto categoryDto){
        logger.debug("Started saveCategory");
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryId(categoryDto.getCategoryId());
        categoryEntity.setCategoryName(categoryDto.getCategoryName());
        categoryEntity.setEnabled(categoryDto.isEnabled());
        LocalDateTime ll = LocalDateTime.now(ZoneId.of(ZoneId.SHORT_IDS.get("PST")));
        categoryEntity.setEnrolledDate(ll);
        categoryEntity.setModifiedDate(ll);

        Mono<CategoryEntity>  ee = categoryRepository.findByCategoryIdAndName(categoryDto.getCategoryId(),categoryDto.getCategoryName());
                ee = ee.map(existingCategory -> {
                    existingCategory.setEnabled(categoryDto.isEnabled());
                    existingCategory.setModifiedDate(ll);
                    return existingCategory;
                }).defaultIfEmpty(categoryEntity);
        logger.debug("ending saveCategory");
       return ee.flatMap( x -> categoryRepository.save(x));

    }

    @Override
    public Mono<Boolean> isCategoryIDEnabled(int categoryId, boolean enabled) {
        return categoryRepository.findByCategoryIdAndEnabled(categoryId, enabled).hasElement();
    }


}
