package com.shipping.analyser.dto;

import com.shipping.analyser.model.ValidationRulesEntity;

import java.time.LocalDateTime;

public class ValidationRulesDto {


    private String type;

    private String variable;

    private String valueType;

    private String value;

    private boolean enabled;

    private String description;

    public ValidationRulesDto() {

    }

    public ValidationRulesDto(String type, String variable, String valueType, String value) {
        this.type = type;
        this.variable = variable;
        this.valueType = valueType;
        this.value = value;
    }

    public ValidationRulesDto(String type, String variable, String valueType, String value, boolean enabled, String description) {
        this.type = type;
        this.variable = variable;
        this.valueType = valueType;
        this.value = value;
        this.enabled = enabled;
        this.description = description;
    }

    public ValidationRulesDto(ValidationRulesEntity validationRulesEntity) {

        this.type = validationRulesEntity.getType();
        this.variable = validationRulesEntity.getVariable();
        this.valueType = validationRulesEntity.getValueType();
        this.value = validationRulesEntity.getValue();
        this.enabled = validationRulesEntity.isEnabled();
        this.description = validationRulesEntity.getDescription();

    }

    public ValidationRulesDto(String type, String variable, String valueType, String value, boolean enabled, String description, LocalDateTime enrolledDate, LocalDateTime modifiedDate) {
        this.type = type;
        this.variable = variable;
        this.valueType = valueType;
        this.value = value;
        this.enabled = enabled;
        this.description = description;

    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}
