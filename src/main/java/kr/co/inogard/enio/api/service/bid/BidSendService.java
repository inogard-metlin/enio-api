package kr.co.inogard.enio.api.service.bid;

import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import kr.co.inogard.enio.api.commons.EnioContext;
import kr.co.inogard.enio.api.commons.constant.EnioMediaType;
import kr.co.inogard.enio.api.domain.agent.Agent;
import kr.co.inogard.enio.api.domain.rfq.RfqDto;
import kr.co.inogard.enio.api.security.ApiUserDetailsImpl;
import kr.co.inogard.enio.api.service.cus.CusSendService;
import kr.co.inogard.enio.api.service.pr.PrSendService;
import kr.co.inogard.enio.api.service.quot.QuotSendService;
import kr.co.inogard.enio.api.service.rfq.RfqSendService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BidSendService {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private PrSendService prSendService;

	@Autowired
	private RfqSendService rfqSendService;

	@Autowired
	private QuotSendService quotSendService;

	@Autowired
	private CusSendService cusSendService;

	@Value("${enio.agent.context-path}/${enio.agent.version}${enio.agent.uri-path.bid.notify-on-channel}")
	private String bidNotifyOnChannelAgentUriPath;

	public void send(String rfqNo) {
		log.info("==================== Send Bid START ====================");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Agent agt = ((ApiUserDetailsImpl) authentication.getPrincipal()).getAgent();
		log.info("rfqNo : {}", rfqNo);

		prSendService.send(rfqNo);
		cusSendService.send(rfqNo);
		rfqSendService.send(rfqNo);
		quotSendService.send(rfqNo);

		RfqDto.NotiCreate cre = new RfqDto.NotiCreate();
		cre.setRfqNo(rfqNo);

		cre.setNotiKind("SEND_COMPLETE");
		cre.setNotiMsg("입찰정보전체송신완료");

		HttpHeaders header = new HttpHeaders();
		header.setAccept(Collections.singletonList(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType()));
		header.setContentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());
		HttpEntity<RfqDto.NotiCreate> requestEntity = new HttpEntity<RfqDto.NotiCreate>(cre, header);

		String url = agt.getConnUrl() + bidNotifyOnChannelAgentUriPath;
		log.info("sendUrl : {}", url);
		RfqDto.NotiResponse res = restTemplate.postForObject(url, requestEntity, RfqDto.NotiResponse.class);
		log.info("rsltCd : {}", res.getRsltCd());
		log.info("rsltMsg : {}", res.getRsltMsg());

		EnioContext.local.remove();
		log.info("==================== Send Bid END ====================");
	}
}
