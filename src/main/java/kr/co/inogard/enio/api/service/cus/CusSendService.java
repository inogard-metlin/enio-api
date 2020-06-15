package kr.co.inogard.enio.api.service.cus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import kr.co.inogard.enio.api.commons.constant.EnioMediaType;
import kr.co.inogard.enio.api.domain.agent.Agent;
import kr.co.inogard.enio.api.domain.cus.Customer;
import kr.co.inogard.enio.api.domain.cus.CustomerDto;
import kr.co.inogard.enio.api.mappers.cus.CusMapper;
import kr.co.inogard.enio.api.security.ApiUserDetailsImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class CusSendService {

  @Autowired
  private CusMapper cusMapper;

  @Autowired
  private ModelMapper modelMapper;

  @Value("${enio.agent.context-path}/${enio.agent.version}${enio.agent.uri-path.cus}")
  private String cusAgentUriPath;
  
  @Autowired
  private RestTemplate restTemplate;

  public void send(String rfqNo) {
    log.info("==================== Send Cus START ====================");
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    Agent agt = ((ApiUserDetailsImpl) authentication.getPrincipal()).getAgent();
    
    List<Customer> cusBasicList = cusMapper.findAllByRfqNo(rfqNo);

    if (cusBasicList.size() > 0) {// 입찰참여업체 기본정보전송
      List<CustomerDto.CreateEntry> creList = new ArrayList<CustomerDto.CreateEntry>();
      for (Iterator<Customer> iter = cusBasicList.iterator(); iter.hasNext();) {
        Customer cus = iter.next();
        CustomerDto.CreateEntry cre = modelMapper.map(cus, CustomerDto.CreateEntry.class);
        creList.add(cre);
      }

      CustomerDto.Create cr = new CustomerDto.Create();
      cr.setDatas(creList);

      HttpHeaders header = new HttpHeaders();
      header.setContentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());
      header.setAccept(Collections.singletonList(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType()));
      HttpEntity<CustomerDto.Create> requestEntity = new HttpEntity<CustomerDto.Create>(cr, header);

      String url = agt.getConnUrl() + cusAgentUriPath;
      log.info("sendUrl : {}", url);
      CustomerDto.Response res = restTemplate.postForObject(url, requestEntity, CustomerDto.Response.class);
      log.debug("rsltCd : {}", res.getRsltCd());
      log.debug("rsltMsg : {}", res.getRsltMsg());
    }
    log.info("==================== Send Cus END ====================");
  }
}
