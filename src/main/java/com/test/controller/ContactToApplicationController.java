package com.test.controller;

import com.test.dao.dto.ContactToApplication;

public interface ContactToApplicationController {

    ContactToApplication getLastById(Long contactId);

}
