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
import kr.co.inogard.enio.api.service.po.PoSendService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/send/po")
public class PoSendRestController {

	@Autowired
	private PoSendService poService;

	@RequestMapping(value = "/{poNo}", method = GET)
	@ResponseStatus(HttpStatus.OK)
	public Map<String, String> createPo(@PathVariable String poNo) {
		log.info("=============== Agent Po Create by Api START ===============");
		log.info("poNo : {}", poNo);
		poService.createPo(poNo);

		Map<String, String> resultMap = new HashMap<String, String>();
		resultMap.put("rsltCd", RsltCd.SUC0000.name());
		resultMap.put("rsltMsg", RsltCd.SUC0000.getCodeNm());
		log.info("=============== Agent Po Create by Api END ===============");
		return resultMap;
	}
}
