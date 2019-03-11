package com.test.service;

import com.test.dao.dto.ContactToApplication;

public interface ContactToApplicationService {

    ContactToApplication getContactWithLastApplication(Long contactId);
}
