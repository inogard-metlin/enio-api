package kr.co.inogard.enio.api.event;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.Locale;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.inogard.enio.api.Application;
import kr.co.inogard.enio.api.commons.constant.EnioMediaType;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class EventRestControllerTest {

  MockMvc mockMvc;

  @Autowired
  WebApplicationContext context;

  @Autowired
  ObjectMapper objectMapper;

  @Before
  public void setup() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
  }

  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  public void getEvents() throws Exception {
    mockMvc.perform(get("/events").accept(MediaType.APPLICATION_JSON_UTF8).locale(Locale.KOREA))
        .andDo(print()).andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "admin", roles = {"ADMIN"})
  public void getEventsWithDatatables() throws Exception {
    mockMvc
        .perform(get("/events").accept(MediaType.APPLICATION_JSON_UTF8).locale(Locale.KOREA)
            .param("draw", "0").param("start", "0").param("length", "10"))
        .andDo(print()).andExpect(status().isOk());
  }

  @Test
  @WithUserDetails("S0037")
  public void getEventsByApi() throws Exception {
    mockMvc
        .perform(get("/api/v1/events")
            .contentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType())
            .accept(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType()).locale(Locale.KOREA))
        .andDo(print()).andExpect(status().isOk());
  }

  @Test
  @WithUserDetails("S0037")
  public void getEventsWithDatatablesByApi() throws Exception {
    mockMvc
        .perform(get("/api/v1/events").accept(MediaType.APPLICATION_JSON_UTF8).locale(Locale.KOREA)
            .param("draw", "0").param("start", "0").param("length", "10"))
        .andDo(print()).andExpect(status().isOk());
  }
}
