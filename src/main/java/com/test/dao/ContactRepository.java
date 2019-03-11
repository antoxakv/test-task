package com.test.dao;

import com.test.dao.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ContactRepository extends JpaRepository<Contact, Long> {

    @Query(value =
            "select need_contact.CONTACT_ID, a.APPLICATION_ID, a.DT_CREATED, a.PRODUCT_NAME " +
            "from (" +
            "       select c.contact_id ," +
            "           max(a.DT_CREATED) DT_CREATED" +
            "       from contact c" +
            "           left join application a on c.contact_id = a.contact_id" +
            "       where c.contact_id  = :id" +
            "       group by c.contact_id" +
            "   ) need_contact" +
            "   left join application a on need_contact.contact_id = a.contact_id" +
            "       and need_contact.DT_CREATED = a.DT_CREATED", nativeQuery = true)
    @Transactional(readOnly = true)
    Object[] getContactWithLastApplication(@Param("id") Long contactId);

}
