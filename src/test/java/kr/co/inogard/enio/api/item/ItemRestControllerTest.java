package kr.co.inogard.enio.api.item;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.inogard.enio.api.Application;
import kr.co.inogard.enio.api.commons.EnioContext;
import kr.co.inogard.enio.api.commons.constant.EnioMediaType;
import kr.co.inogard.enio.api.commons.util.CryptoUtil;
import kr.co.inogard.enio.api.domain.item.ItemDto;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@Slf4j
public class ItemRestControllerTest {

  MockMvc mvc;

  @Autowired
  WebApplicationContext context;

  @Autowired
  ObjectMapper objectMapper;

  // @Autowired
  // private ModelMapper modelMapper;

  @Autowired
  private CryptoUtil cryptoUtil;

  @Autowired
  private RestTemplate restTemplate;

  ItemDto.CreateWrapper dto;

  @Before
  public void setup() {
    // mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    mvc = MockMvcBuilders.webAppContextSetup(context).build();
    
    List<ItemDto.Create> datas = new ArrayList<ItemDto.Create>();
    ItemDto.Create item = new ItemDto.Create();
    item.setItemCd("1000001");
    item.setItemNm("물품");
    datas.add(item);
    
    dto = new ItemDto.CreateWrapper();
    dto.setDatas(datas);
  }

  @Test
  @WithUserDetails("S0037")
  public void createItemTest() throws Exception {
    String content = objectMapper.writeValueAsString(dto);

    MvcResult result = mvc
        .perform(post("/api/items")
            .contentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType())
            .accept(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType()).locale(Locale.KOREA)
            .content(cryptoUtil.encryptString("ENIOS0037-201708", content)))
        .andDo(print()).andExpect(status().isCreated()).andReturn();

    log.debug("response : {}",
        cryptoUtil.decryptString("ENIOS0037-201708", result.getResponse().getContentAsString()));
  }

  @Test
  public void createItemRestTemplateTest() throws Exception {
    HttpHeaders header = new HttpHeaders();
    header.setContentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());
    header.setAccept(
        Collections.singletonList(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType()));

    HttpEntity<ItemDto.CreateWrapper> requestEntity =
        new HttpEntity<ItemDto.CreateWrapper>(dto, header);

    EnioContext.local.set(new EnioContext("S0037", "ENIOS0037-201708"));
    try {
      ItemDto.ResponseWrapper res = restTemplate.postForObject("http://localhost:8080/api/items",
          requestEntity, ItemDto.ResponseWrapper.class);
      log.debug("res : {}", res);
    } catch (HttpStatusCodeException e) {
      e.getResponseBodyAsString();
      log.debug("error : {}", e.getResponseBodyAsString());
    }
    EnioContext.local.remove();
  }
}
