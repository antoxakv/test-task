package service;

import com.test.Bootstrap;
import com.test.dao.ContactRepository;
import com.test.dao.dto.ContactToApplication;
import com.test.service.ContactToApplicationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {Bootstrap.class})
public class ContactToApplicationServiceTest {

    @Autowired
    private ContactToApplicationService service;

    @MockBean
    private ContactRepository repository;

    private Date today = new Date();

    @Before
    public void init() {
        Mockito.when(repository.getContactWithLastApplication(1L))
                .thenReturn(null);
        Mockito.when(repository.getContactWithLastApplication(2L))
                .thenReturn(new Object[0]);
        Mockito.when(repository.getContactWithLastApplication(3L))
                .thenReturn(new Object[][]{{1L, null, null, null}});
        Mockito.when(repository.getContactWithLastApplication(4L))
                .thenReturn(new Object[][]{{2L, 10L, today, "problem"}});
    }

    @Test
    public void notFoundContactToApplication() {
        Assert.assertNull(service.getContactWithLastApplication(1L));
        Assert.assertNull(service.getContactWithLastApplication(2L));
    }

    @Test
    public void findOnlyContact() {
        ContactToApplication cta = service.getContactWithLastApplication(3L);
        Assert.assertEquals(1L, cta.getContactId().longValue());
        Assert.assertNull(cta.getApplicationId());
        Assert.assertNull(cta.getDtCreated());
        Assert.assertNull(cta.getProductName());
    }

    @Test
    public void findContactToApplication(){
        ContactToApplication cta = service.getContactWithLastApplication(4L);
        Assert.assertEquals(2L, cta.getContactId().longValue());
        Assert.assertEquals(10L, cta.getApplicationId().longValue());
        Assert.assertEquals(today, cta.getDtCreated());
        Assert.assertEquals("problem", cta.getProductName());
    }
}
