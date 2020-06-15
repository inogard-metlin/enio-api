package kr.co.inogard.enio.api.cus;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import kr.co.inogard.enio.api.Application;
import kr.co.inogard.enio.api.commons.EnioContext;
import kr.co.inogard.enio.api.commons.constant.EnioMediaType;
import kr.co.inogard.enio.api.commons.constant.EnioServerType;
import kr.co.inogard.enio.api.domain.agent.Agent;
import kr.co.inogard.enio.api.domain.cus.Customer;
import kr.co.inogard.enio.api.domain.cus.CustomerDto;
import kr.co.inogard.enio.api.service.cus.CusService;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@Slf4j
public class CusServiceTest {

  @Autowired
  private CusService cusService;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private RestTemplate restTemplate;


  @Test
  public void findListByRfqNoTest() {
    try {
      
    } catch (Exception e) {
      
    }
    EnioServerType.valueOf("aaa");
   /* String rfqNo = "S0001202014041500001";
    List<Customer> cusList = cusService.getAllByRfqNo(rfqNo);

    log.debug("list-size() : {}", cusList.size());
    assertThat(2, is(cusList.size()));*/


  }

  @Test
  public void sendCusListTest() {
    String rfqNo = "S0001202014041500001";
    List<Customer> cusList = cusService.getAllByRfqNo(rfqNo);

    if (cusList.size() < 1)
      return;


    List<CustomerDto.CreateEntry> creList = new ArrayList<CustomerDto.CreateEntry>();
    for (Iterator<Customer> iter = cusList.iterator(); iter.hasNext();) {
      Customer cus = iter.next();
      CustomerDto.CreateEntry cre = modelMapper.map(cus, CustomerDto.CreateEntry.class);
      creList.add(cre);
    }
    CustomerDto.Create cr = new CustomerDto.Create();
    cr.setDatas(creList);

    Agent agt = new Agent();
    agt.setAgtCd("S0037");
    agt.setLicenceKey("RU5JT1MwMDM3LTIwMTcwOA==");

    EnioContext.local.set(new EnioContext(agt.getAgtCd(), agt.getRawLicenceKey()));

    HttpHeaders header = new HttpHeaders();
    header.setContentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());
    HttpEntity<CustomerDto.Create> requestEntity = new HttpEntity<CustomerDto.Create>(cr, header);


    String url = "http://localhost:8081/agent/v1/cus";
    CustomerDto.Response res = null;
    try {
      res = restTemplate.postForObject(url, requestEntity, CustomerDto.Response.class);
      if ("SUC0000".equals(res.getRsltCd())) {
        List<CustomerDto.ResponseEntry> rcvList = res.getDatas();
        for (Iterator<CustomerDto.ResponseEntry> iter = rcvList.iterator(); iter.hasNext();) {
          CustomerDto.ResponseEntry re = iter.next();

        }
      }
    } catch (HttpClientErrorException e) {
      e.getResponseBodyAsString();

    }

    EnioContext.local.remove();


  }

}
