package com.test.dao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class ContactToApplication {

    @JsonProperty("CONTACT_ID")
    private Long contactId;

    @JsonProperty("APPLICATION_ID")
    private Long applicationId;

    @JsonProperty("DT_CREATED")
    private Date dtCreated;

    @JsonProperty("PRODUCT_NAME")
    private String productName;

    public ContactToApplication() {
    }

    public ContactToApplication(Long contactId, Long applicationId, Date dtCreated, String productName) {
        this.contactId = contactId;
        this.applicationId = applicationId;
        this.dtCreated = dtCreated;
        this.productName = productName;
    }

    public Long getContactId() {
        return contactId;
    }

    public void setContactId(Long contactId) {
        this.contactId = contactId;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Date getDtCreated() {
        return dtCreated;
    }

    public void setDtCreated(Date dtCreated) {
        this.dtCreated = dtCreated;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
