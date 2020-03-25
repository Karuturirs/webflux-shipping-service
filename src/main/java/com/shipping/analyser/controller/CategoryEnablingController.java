package com.shipping.analyser.controller;


import com.shipping.analyser.dto.CategoryDto;
import com.shipping.analyser.pojo.Category;
import com.shipping.analyser.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path="/api/v1/shipping/category")
public class CategoryEnablingController {

    private static final Logger logger = LoggerFactory.getLogger(CategoryEnablingController.class);

    @Autowired
    CategoryService categoryService;

    @Value("${admin.authenticate.token}")
    private String token;

    /***
     * Fetches all the categories that are available by the system.
     * Queryparam enabled=ALL will give all the categories with enabled and disabled.
     * @param enabled (values: All, true, false)
     * @return
     */
    @GetMapping(produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<CategoryDto> findAllCategories(@RequestParam(name = "enabled" ,defaultValue = "ALL") String enabled){
        return (enabled.equalsIgnoreCase("ALL")) ? categoryService.findAllCategory() :
                ((enabled.equalsIgnoreCase("true")) ? categoryService.findAllCategoryEnabled(true) :
                        categoryService.findAllCategoryEnabled(false) ) ;
    }

    /**
     * Adding/Updating new category into the rules engine for new shipping program
     * @param authToken (token to authenticate the api, property file value)
     * @param category
     * @return
     */
    @PostMapping
    public Mono<?> createOrUpdateCategory(@RequestHeader(name = "X-Admin-Authentication", required = true) String authToken,
                                  @RequestBody(required = true) Category category){
        //Validate Token  and inputs
        if(authToken.equals(token) ){
            if(category!=null && category.getCategoryName()!= null){
                CategoryDto categoryDto = new CategoryDto(category.getCategoryId(), category.getCategoryName(), category.isEnabled());
                return categoryService.saveCategory(categoryDto);
            }else{
                return Mono.just(new ResponseEntity(HttpStatus.BAD_REQUEST));
            }
        }else{
            return Mono.just(new ResponseEntity(HttpStatus.UNAUTHORIZED));
        }
    }


}
