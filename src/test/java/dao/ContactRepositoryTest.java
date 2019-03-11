package dao;

import com.test.Bootstrap;
import com.test.dao.ContactRepository;
import com.test.dao.entity.Application;
import com.test.dao.entity.Contact;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;

@RunWith(SpringRunner.class)
@DataJpaTest
@ContextConfiguration(classes = {Bootstrap.class})
public class ContactRepositoryTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private ContactRepository repository;

    @Test
    public void findLastAppWithManyApps() {
        Contact contact = new Contact();
        Application insurance = new Application(getDate(2), "insurance", contact);
        Application problem = new Application(getDate(10), "problem", contact);
        Application card = new Application(new Date(), "card", contact);
        contact.setApplications(Arrays.asList(card, insurance, problem));
        em.persist(contact);
        em.persist(card);
        em.persist(insurance);
        em.persistAndFlush(problem);

        Object rawCta[] = (Object[]) repository.getContactWithLastApplication(contact.getContactId())[0];
        Assert.assertEquals(contact.getContactId().longValue(), ((Number) rawCta[0]).longValue());
        Assert.assertEquals(card.getApplicationID().longValue(), ((Number) rawCta[1]).longValue());
        Assert.assertEquals(card.getDtCreated(), rawCta[2]);
        Assert.assertEquals(card.getProductName(), rawCta[3]);
    }

    @Test
    public void findLastAppWithOneApp() {
        Contact contact = new Contact();
        Application insurance = new Application(getDate(2), "insurance", contact);
        contact.setApplications(Arrays.asList(insurance));
        em.persist(contact);
        em.persistAndFlush(insurance);

        Object rawCta[] = (Object[]) repository.getContactWithLastApplication(contact.getContactId())[0];
        Assert.assertEquals(contact.getContactId().longValue(), ((Number) rawCta[0]).longValue());
        Assert.assertEquals(insurance.getApplicationID().longValue(), ((Number) rawCta[1]).longValue());
        Assert.assertEquals(insurance.getDtCreated(), rawCta[2]);
        Assert.assertEquals(insurance.getProductName(), rawCta[3]);
    }

    @Test
    public void notFoundApplication() {
        Contact contact = new Contact();
        em.persistAndFlush(contact);

        Object rawCta[] = (Object[]) repository.getContactWithLastApplication(contact.getContactId())[0];
        Assert.assertEquals(contact.getContactId().longValue(), ((Number) rawCta[0]).longValue());
        Assert.assertNull(rawCta[1]);
        Assert.assertNull(rawCta[2]);
        Assert.assertNull(rawCta[3]);
    }

    @Test
    public void notFountContact() {
        Long lastId = em.getEntityManager()
                .createQuery("select max(c.id) from Contact c", Long.class)
                .getSingleResult() + 1;
        Assert.assertEquals(0, repository.getContactWithLastApplication(lastId).length);
    }

    private Date getDate(int minusDays) {
        return Date.from(LocalDateTime.now()
                .minusDays(minusDays)
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }
}
