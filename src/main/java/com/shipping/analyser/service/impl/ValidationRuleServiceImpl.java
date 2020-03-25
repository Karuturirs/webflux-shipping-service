package com.shipping.analyser.service.impl;

import com.shipping.analyser.business.EnrollmentBusinessFacet;
import com.shipping.analyser.dto.CategoryDto;
import com.shipping.analyser.dto.ValidationRulesDto;
import com.shipping.analyser.model.CategoryEntity;
import com.shipping.analyser.model.ValidationRulesEntity;
import com.shipping.analyser.pojo.SellerItem;
import com.shipping.analyser.repository.ValidationRuleRepository;
import com.shipping.analyser.service.ValidationRuleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class ValidationRuleServiceImpl implements ValidationRuleService {

    private static final Logger logger = LoggerFactory.getLogger(ValidationRuleServiceImpl.class);

    @Autowired
    ValidationRuleRepository validationRuleRepository;

    @Autowired
    EnrollmentBusinessFacet enrollmentBusinessFacet;

    @Override
    public Flux<ValidationRulesDto> findAllRules() {
        return validationRuleRepository.findAll().map( x -> new ValidationRulesDto(x));
    }

    @Override
    public Flux<ValidationRulesDto> findAllRulesEnabled(boolean enabled) {
        return validationRuleRepository.findByEnabled(enabled).map(x-> new ValidationRulesDto(x)).log();
    }

    @Override
    public Mono<?> saveValidationRule(ValidationRulesDto validationRulesDto) {
        logger.debug("Started saveValidationRule");
        ValidationRulesEntity ve = new ValidationRulesEntity();
        ve.setType(validationRulesDto.getType());
        ve.setVariable(validationRulesDto.getVariable());
        ve.setValue(validationRulesDto.getValue());
        ve.setValueType(validationRulesDto.getValueType());
        ve.setEnabled(validationRulesDto.isEnabled());
        ve.setDescription(validationRulesDto.getDescription());
        LocalDateTime ll = LocalDateTime.now(ZoneId.of(ZoneId.SHORT_IDS.get("PST")));
        ve.setEnrolledDate(ll);
        ve.setModifiedDate(ll);

        Mono<ValidationRulesEntity>  ee = validationRuleRepository.findByTypeAndVariable(validationRulesDto.getType(),validationRulesDto.getVariable());
        ee = ee.map(existingRule -> {
            existingRule.setValue(validationRulesDto.getValue());
            existingRule.setEnabled(validationRulesDto.isEnabled());
            existingRule.setDescription(validationRulesDto.getDescription());
            existingRule.setModifiedDate(ll);
            return existingRule;
        }).defaultIfEmpty(ve);
        logger.debug("ending saveValidationRule");
        return ee.flatMap( x -> validationRuleRepository.save(x));

    }

    @Override
    public Mono<?> isEligibleForNewShipping(SellerItem sellerItem) {
        logger.debug("Started isEligibleForNewShipping");
        return findAllRulesEnabled(true).flatMap( rule -> enrollmentBusinessFacet.genericValidateOnRule(rule, sellerItem))
                .reduce(true, (x,y)-> x&y).log();
    }

}
