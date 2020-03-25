package com.shipping.analyser.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;


public class SellerDto {

    public SellerDto(String username) {
        this.username = username;
    }

    public SellerDto(String username, LocalDateTime enrolledDate, LocalDateTime modifiedDate) {
        this.username = username;
        this.enrolledDate = enrolledDate;
        this.modifiedDate = modifiedDate;
    }

    private String username;

    private java.time.LocalDateTime enrolledDate;

    private java.time.LocalDateTime modifiedDate;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
