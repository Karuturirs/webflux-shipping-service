package com.shipping.analyser.business;

import com.shipping.analyser.controller.CategoryEnablingController;
import com.shipping.analyser.dto.ValidationRulesDto;
import com.shipping.analyser.pojo.SellerItem;
import com.shipping.analyser.service.CategoryService;
import com.shipping.analyser.service.SellerEnrollmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;


@Component
public class EnrollmentBusinessFacet {

    private static final Logger logger = LoggerFactory.getLogger(EnrollmentBusinessFacet.class);

    @Autowired
    CategoryService categoryService;

    @Autowired
    SellerEnrollmentService sellerEnrollmentService;

    /**
     * This method is the core for doing the validation for all the rules dynamically
     * except from the tables and property configuration.
     * Developed dynamically so that in future with less code change any validation can be managed through rest API's.
     * @param validationRulesDto
     * @param sellerItem
     * @return
     */
    public Mono<Boolean> genericValidateOnRule(ValidationRulesDto validationRulesDto, SellerItem sellerItem ){

        logger.debug("Started validateOnRule");
        java.lang.reflect.Method method;
        String variable = validationRulesDto.getVariable();

        StringBuilder getVariable =  new StringBuilder();
        getVariable.append("get");
        getVariable.append(variable.substring(0, 1).toUpperCase() + variable.substring(1));

        try {
            method = sellerItem.getClass().getMethod(getVariable.toString());
            String threshold = validationRulesDto.getValue();
            // Rule to check categories
            if(threshold.equals("rule.categories")){
                return categoryService.isCategoryIDEnabled(sellerItem.getCategoryId(), true);
            }
            // Rule to check if Seller is Enrolled to new shipping program
            else if(threshold.equals("rule.enrollment")){
                return sellerEnrollmentService.isSellerEnrolled(sellerItem.getUsername());
            }
            // Any other dynamic property value check depending on property configuration in DB.
            else {

                String operation = validationRulesDto.getType();

                if (validationRulesDto.getValueType() != null) {
                    switch (validationRulesDto.getValueType()) {
                        case "int":
                        case "float":
                        case "double":
                            double dd = Double.parseDouble(threshold);
                            double price = (validationRulesDto.getValueType().equals("int")) ? (int) method.invoke(sellerItem) : (validationRulesDto.getValueType().equals("float")) ? (float) method.invoke(sellerItem) : (double) method.invoke(sellerItem);
                            if (operation.equals("==")) {
                                return Mono.just(dd == price);
                            } else if (operation.equals("<")) {
                                return Mono.just(price < dd);
                            } else if (operation.equals(">")) {
                                return Mono.just(price > dd);
                            } else if (operation.equals("<=")) {
                                return Mono.just(price < dd || price == dd);
                            } else if (operation.equals(">=")) {
                                return Mono.just(price > dd || price == dd);
                            } else { // not equals
                                return Mono.just(price != dd);
                            }
                        case "boolean":
                            boolean flag = Boolean.valueOf(threshold);
                            boolean val = (boolean) method.invoke(sellerItem);
                            return Mono.just(operation.equals("==") ? flag == val : flag != val);
                        default:
                            String value = (String) method.invoke(sellerItem);
                            return Mono.just(operation.equals("==") ? threshold.equals(value) : !threshold.equals(value));

                    }

                }
            }

        }catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e1) {
            e1.printStackTrace();
        } catch (Exception ex){
            Mono.error(new Throwable(ex));
        }
        logger.debug("end of validateOnRule");
        return Mono.just(false);

    }

}
