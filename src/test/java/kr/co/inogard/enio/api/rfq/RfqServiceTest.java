package kr.co.inogard.enio.api.rfq;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import kr.co.inogard.enio.api.Application;
import kr.co.inogard.enio.api.commons.constant.EnioMediaType;
import kr.co.inogard.enio.api.commons.handler.EnioValidateHandler;
import kr.co.inogard.enio.api.domain.rfq.Rfq;
import kr.co.inogard.enio.api.domain.rfq.RfqDto;
import kr.co.inogard.enio.api.service.rfq.RfqService;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@Slf4j
public class RfqServiceTest {

  @Autowired
  RfqService rfqService;

  @Autowired
  ModelMapper modelMapper;

  @Autowired
  RestTemplate restTemplate;

  @Autowired
  EnioValidateHandler enioValidateHandler;

  @Value("${enio.agent.context-path}/${enio.agent.version}${enio.agent.uri-path.pr}")
  private String prUriPath;
  
  @Value("${enio.agent.context-path}/${enio.agent.version}${enio.agent.uri-path.bid.rfq}")
  private String rfqAgentUriPath;
  
  @Test
  public void test() {
    log.debug("agentVersion : {}", prUriPath);
  }

  @Test
  public void createFromPrText() {
    String prNo = "S0001102017111300002";

    Map<String, Object> prInfo = new HashMap<String, Object>();
    prInfo.put("prNo", prNo);
    prInfo.put("bidSubTypecd", "4");
    prInfo.put("bidTypecd", "1");
    prInfo.put("selectedBidderStd", "price");
    prInfo.put("bidderRate", null);
    prInfo.put("bidExpireDt", null);
    prInfo.put("rfqdocYn", null);
    prInfo.put("rfqdocExpireDt", null);
    prInfo.put("rfqdocRmrk", null);
    prInfo.put("techdocYn", null);
    prInfo.put("techdocExpireDt", null);
    prInfo.put("techdocRmrk", null);
    
    Rfq rfq = rfqService.createFromPr(prInfo);
    log.debug("new-rfq-no=:{}", rfq.getRfqNo());
  }
  
  @Test
  //@WithMockApiUserDetails
  public void createRfqTest() {
    RfqDto.Create create = new RfqDto.Create();
    
    HttpHeaders header = new HttpHeaders();
    header.setAccept(
        Collections.singletonList(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType()));
    header.setContentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());
    HttpEntity<RfqDto.Create> requestEntity = new HttpEntity<RfqDto.Create>(create, header);

    String url = "http://localhost:8081/agent/v1/bid";
    log.info("sendUrl : {}", url);

    RfqDto.Response res = restTemplate.postForObject(url, requestEntity, RfqDto.Response.class);
    log.info("rsltCd : {}", res.getRsltCd());
    log.info("rsltMsg : {}", res.getRsltMsg());
  }
}
