package com.shipping.analyser.service;

import com.shipping.analyser.dto.ValidationRulesDto;
import com.shipping.analyser.model.ValidationRulesEntity;
import com.shipping.analyser.pojo.SellerItem;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ValidationRuleService {

    public Flux<ValidationRulesDto> findAllRules();

    public Flux<ValidationRulesDto> findAllRulesEnabled(boolean enabled);

    public Mono<?> saveValidationRule(ValidationRulesDto validationRulesDto);

    public Mono<?> isEligibleForNewShipping(SellerItem sellerItem);
}
