package kr.co.inogard.enio.api.web;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import kr.co.inogard.enio.api.commons.constant.RsltCd;
import kr.co.inogard.enio.api.service.bid.BidSendService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/send/bid")
public class BidSendRestController {

  @Autowired
  BidSendService bidService;

  @RequestMapping(value = "/{rfqNo}", method = GET)
  @ResponseStatus(HttpStatus.CREATED)
  public Map<String, String> create(@PathVariable("rfqNo") String rfqNo) {
    log.info("=============== Agent Bid Create by Api START ===============");
    log.info("rfqNo : {}", rfqNo);
    bidService.send(rfqNo);

    Map<String, String> resultMap = new HashMap<String, String>();
    resultMap.put("rsltCd", RsltCd.SUC0000.name());
    resultMap.put("rsltMsg", RsltCd.SUC0000.getCodeNm());
    log.info("=============== Agent Bid Create by Api END ===============");
    return resultMap;
  }
}
