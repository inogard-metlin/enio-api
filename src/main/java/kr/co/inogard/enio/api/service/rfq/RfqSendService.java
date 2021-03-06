package kr.co.inogard.enio.api.service.rfq;

import java.util.Collections;
import java.util.List;
import javax.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import kr.co.inogard.enio.api.commons.constant.EnioMediaType;
import kr.co.inogard.enio.api.commons.handler.EnioValidateHandler;
import kr.co.inogard.enio.api.domain.agent.Agent;
import kr.co.inogard.enio.api.domain.rfq.Rfq;
import kr.co.inogard.enio.api.domain.rfq.RfqCus;
import kr.co.inogard.enio.api.domain.rfq.RfqCusDto;
import kr.co.inogard.enio.api.domain.rfq.RfqDegree;
import kr.co.inogard.enio.api.domain.rfq.RfqDegreeDto;
import kr.co.inogard.enio.api.domain.rfq.RfqDto;
import kr.co.inogard.enio.api.domain.rfq.RfqItem;
import kr.co.inogard.enio.api.domain.rfq.RfqItemDto;
import kr.co.inogard.enio.api.domain.rfq.RfqSrv;
import kr.co.inogard.enio.api.mappers.rfq.RfqCusMapper;
import kr.co.inogard.enio.api.mappers.rfq.RfqDegreeMapper;
import kr.co.inogard.enio.api.mappers.rfq.RfqItemMapper;
import kr.co.inogard.enio.api.mappers.rfq.RfqMapper;
import kr.co.inogard.enio.api.mappers.rfq.RfqSrvMapper;
import kr.co.inogard.enio.api.security.ApiUserDetailsImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class RfqSendService {

	@Autowired
	private RfqMapper rfqMapper;

	@Autowired
	private RfqCusMapper rfqCusMapper;

	@Autowired
	private RfqDegreeMapper rfqDegreeMapper;

	@Autowired
	private RfqItemMapper rfqItemMapper;

	@Autowired
	private RfqSrvMapper rfqSrvMapper;

	@Autowired
	private EnioValidateHandler enioValidateHandler;

	@Autowired
	private ModelMapper modelMapper;

	@Value("${enio.agent.context-path}/${enio.agent.version}${enio.agent.uri-path.bid.rfq}")
	private String rfqAgentUriPath;

	@Autowired
	private RestTemplate restTemplate;

	public void send(String rfqNo) {
		log.info("==================== Send Rfq START ====================");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Agent agt = ((ApiUserDetailsImpl) authentication.getPrincipal()).getAgent();

		Rfq rfq = rfqMapper.findByRfqNo(rfqNo);

		if (rfq == null) {
			log.info("No data to send");
			return;
		}

		RfqDto.Create create = modelMapper.map(rfq, RfqDto.Create.class);
		List<RfqItem> rfqItems = rfqItemMapper.findAllByRfqNo(rfqNo);
		List<RfqSrv> rfqSrvs = null;
		for (RfqItem rfqItem : rfqItems) {
			rfqSrvs = rfqSrvMapper.findAllbyRfqItem(rfqItem);
			rfqItem.setRfqSrvList(rfqSrvs);
		}

		List<RfqCus> rfqCusList = rfqCusMapper.findAllByRfqNo(rfqNo);
		List<RfqDegree> rfqDegreeList = rfqDegreeMapper.findAllByRfqNo(rfqNo);

		List<RfqItemDto.Create> sendRfqItems = (modelMapper.map(rfqItems, new TypeToken<List<RfqItemDto.Create>>() {}.getType()));

		List<RfqCusDto.Create> sendRfqCusList = modelMapper.map(rfqCusList, new TypeToken<List<RfqCusDto.Create>>() {}.getType());

		List<RfqDegreeDto.Create> sendRfqDegreeList = modelMapper.map(rfqDegreeList, new TypeToken<List<RfqDegreeDto.Create>>() {}.getType());

		create.setRfqItemList(sendRfqItems);
		create.setRfqCusList(sendRfqCusList);
		create.setRfqDegreeList(sendRfqDegreeList);

		enioValidateHandler.validate(create);

		HttpHeaders header = new HttpHeaders();
		header.setAccept(Collections.singletonList(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType()));
		header.setContentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());
		HttpEntity<RfqDto.Create> requestEntity = new HttpEntity<RfqDto.Create>(create, header);

		String url = agt.getConnUrl() + rfqAgentUriPath;
		log.info("sendUrl : {}", url);

		RfqDto.Response res = restTemplate.postForObject(url, requestEntity, RfqDto.Response.class);
		log.info("rsltCd : {}", res.getRsltCd());
		log.info("rsltMsg : {}", res.getRsltMsg());
		log.info("==================== Send Rfq END ====================");
	}
}
