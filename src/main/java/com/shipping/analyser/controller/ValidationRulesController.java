package com.shipping.analyser.controller;


import com.shipping.analyser.dto.CategoryDto;
import com.shipping.analyser.dto.ValidationRulesDto;
import com.shipping.analyser.pojo.Category;
import com.shipping.analyser.pojo.SellerItem;
import com.shipping.analyser.service.CategoryService;
import com.shipping.analyser.service.ValidationRuleService;
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
@RequestMapping(path="/api/v1/shipping")
public class ValidationRulesController {

    private static final Logger logger = LoggerFactory.getLogger(ValidationRulesController.class);

    @Autowired
    ValidationRuleService validationRuleService;

    @Value("${admin.authenticate.token}")
    private String token;

    /***
     * Fetches all the validation rules that are available by the system.
     * Queryparam enabled=ALL will give all the rules with enabled and disabled.
     * @param enabled (values: All, true, false)
     * @return
     */
    @GetMapping(path="/rules",produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<?> findAllValidationRules(@RequestParam(name = "enabled" ,defaultValue = "ALL") String enabled){
        return (enabled.equalsIgnoreCase("ALL")) ? validationRuleService.findAllRules() :
                ((enabled.equalsIgnoreCase("true")) ? validationRuleService.findAllRulesEnabled(true) :
                        validationRuleService.findAllRulesEnabled(false) ) ;
    }

    /**
     * Adding/Updating new rules into the rules engine for new shipping program
     * @param authToken
     * @param validationRulesDto
     * @return
     */
    @PostMapping(path="/rules")
    public Mono<?> createOrUpdateRules(@RequestHeader(name = "X-Admin-Authentication", required = true) String authToken,
                                  @RequestBody(required = true) ValidationRulesDto validationRulesDto){
        //Validate Token  and inputs
        if(authToken.equals(token) ){
            if(validationRulesDto!=null && validationRulesDto.getType() != null && validationRulesDto.getVariable()!= null){
                return validationRuleService.saveValidationRule(validationRulesDto);
            }else{
                return Mono.just(new ResponseEntity(HttpStatus.BAD_REQUEST));
            }
        }else{
            return Mono.just(new ResponseEntity(HttpStatus.UNAUTHORIZED));
        }
    }

    /**
     * Finds if the sellers item is qualified for new shipping or not
     * @param sellerItem
     * @return
     */
    @PostMapping(path="/validator")
    public Mono<?> newShippingEligibilityChecker(@RequestBody(required = true) SellerItem sellerItem){

        if(sellerItem!= null && sellerItem.getTitle() != null && sellerItem.getUsername()!= null ){
            return validationRuleService.isEligibleForNewShipping(sellerItem);
        }else{
            return Mono.just(new ResponseEntity(HttpStatus.BAD_REQUEST));
        }
    }


}
