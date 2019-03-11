package com.test.controller;

import com.test.dao.dto.ContactToApplication;
import com.test.service.ContactToApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping(value = "/contact-application")
public class ContactToApplicationControllerImpl implements ContactToApplicationController {

    private ContactToApplicationService service;

    @GetMapping(value = "/{contactId}")
    public ContactToApplication getLastById(@PathVariable Long contactId) {
        if (contactId == null || contactId < 1) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ContactId must be positive number.");
        }
        ContactToApplication cta = service.getContactWithLastApplication(contactId);
        if (cta == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Contact not found.");
        }
        return cta;
    }

    @Autowired
    public void setService(ContactToApplicationService service) {
        this.service = service;
    }
}

