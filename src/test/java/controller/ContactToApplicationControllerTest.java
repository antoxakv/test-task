package controller;

import com.test.Bootstrap;
import com.test.controller.ContactToApplicationController;
import com.test.dao.dto.ContactToApplication;
import com.test.service.ContactToApplicationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(ContactToApplicationController.class)
@ContextConfiguration(classes = {Bootstrap.class})
public class ContactToApplicationControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ContactToApplicationService service;

    @Test
    public void findContactToApplication() throws Exception {
        Date date = Date.from(LocalDateTime.of(2019, 3, 10, 12, 0, 0)
                .atZone(ZoneId.systemDefault())
                .toInstant());
        checkCorrectResponse(new ContactToApplication(1L, 1L, date, "card"),
                "{\"CONTACT_ID\": 1,\"APPLICATION_ID\": 1," +
                        "\"DT_CREATED\": \"2019-03-10T08:00:00.000+0000\",\"PRODUCT_NAME\": \"card\"}");
    }

    @Test
    public void findOnlyContactId() throws Exception {
        checkCorrectResponse(new ContactToApplication(2L, null, null, null),
                "{\"CONTACT_ID\": 2,\"APPLICATION_ID\": null,\"DT_CREATED\": null,\"PRODUCT_NAME\": null}");
    }

    @Test
    public void notFoundContact() throws Exception {
        checkError(3L, HttpStatus.NOT_FOUND, status().isNotFound(), "Contact not found.");
    }

    @Test
    public void incorrectContactId() throws Exception {
        checkError(4L, HttpStatus.BAD_REQUEST, status().isBadRequest(), "ContactId must be positive number.");
    }

    private void checkCorrectResponse(ContactToApplication cta, String jsonContent) throws Exception {
        given(service.getContactWithLastApplication(cta.getContactId())).willReturn(cta);
        mvc.perform(get("/contact-application/{contactId}", cta.getContactId()))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonContent));
    }

    private void checkError(Long contactId, HttpStatus status, ResultMatcher rm, String errorMsg) throws Exception {
        given(service.getContactWithLastApplication(contactId))
                .willThrow(new ResponseStatusException(status, errorMsg));
        mvc.perform(get("/contact-application/{contactId}", contactId))
                .andExpect(rm)
                .andExpect(status().reason(errorMsg));
    }
}