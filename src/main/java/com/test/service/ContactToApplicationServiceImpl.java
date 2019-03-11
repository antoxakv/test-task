package com.test.service;

import com.test.dao.ContactRepository;
import com.test.dao.dto.ContactToApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ContactToApplicationServiceImpl implements ContactToApplicationService {

    private ContactRepository contactRepository;

    public ContactToApplication getContactWithLastApplication(Long contactId) {
        Object[] rawCta = contactRepository.getContactWithLastApplication(contactId);
        if (rawCta == null || rawCta.length == 0) {
            return null;
        }
        rawCta = ((Object[]) rawCta[0]);
        Number applicationId = (Number) rawCta[1];
        return new ContactToApplication(((Number) rawCta[0]).longValue(),
                applicationId == null ? null : applicationId.longValue(),
                (Date) rawCta[2],
                (String) rawCta[3]);
    }

    @Autowired
    public void setContactRepository(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }
}
