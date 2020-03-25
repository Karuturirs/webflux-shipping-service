package com.shipping.analyser.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

public class CategoryDto {

    public CategoryDto(int categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public CategoryDto(int categoryId, String categoryName, boolean enabled) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.enabled = enabled;
    }

    public CategoryDto(int categoryId, String categoryName, boolean enabled, LocalDateTime modifiedDate) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.enabled = enabled;
        this.modifiedDate = modifiedDate;
    }

    public CategoryDto(int categoryId, String categoryName, boolean enabled, LocalDateTime enrolledDate, LocalDateTime modifiedDate) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.enabled = enabled;
        this.enrolledDate = enrolledDate;
        this.modifiedDate = modifiedDate;
    }

    private int categoryId;

    private String categoryName;

    private boolean enabled;

    private java.time.LocalDateTime enrolledDate;

    private java.time.LocalDateTime modifiedDate;

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public LocalDateTime getEnrolledDate() {
        return enrolledDate;
    }

    public void setEnrolledDate(LocalDateTime enrolledDate) {
        this.enrolledDate = enrolledDate;
    }

    public LocalDateTime getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(LocalDateTime modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
