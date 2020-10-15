package kr.co.inogard.enio.api.service.pr;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import kr.co.inogard.enio.api.commons.EnioFileSystemResource;
import kr.co.inogard.enio.api.commons.constant.EnioMediaType;
import kr.co.inogard.enio.api.commons.constant.RsltCd;
import kr.co.inogard.enio.api.commons.handler.EnioFileHandler;
import kr.co.inogard.enio.api.domain.agent.Agent;
import kr.co.inogard.enio.api.domain.ftp.FtpFileDto;
import kr.co.inogard.enio.api.domain.pr.Pr;
import kr.co.inogard.enio.api.domain.pr.PrDto;
import kr.co.inogard.enio.api.domain.pr.PrFile;
import kr.co.inogard.enio.api.domain.pr.PrItem;
import kr.co.inogard.enio.api.domain.pr.PrItemDto;
import kr.co.inogard.enio.api.domain.pr.PrMap;
import kr.co.inogard.enio.api.domain.pr.PrSrv;
import kr.co.inogard.enio.api.domain.pr.PrSrvDto;
import kr.co.inogard.enio.api.mappers.pr.PrMapMapper;
import kr.co.inogard.enio.api.security.ApiUserDetailsImpl;
import kr.co.inogard.enio.api.service.ftp.FtpService;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class PrSendService {

	@Autowired
	private PrService prService;

	@Autowired
	private PrMapMapper prMapMapper;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private EnioFileHandler enioFileHandler;

	@Autowired
	private FtpService ftpService;

	@Value("${enio.agent.context-path}/${enio.agent.version}${enio.agent.uri-path.pr}")
	private String prAgentUriPath;

	@Autowired
	private RestTemplate restTemplate;

	public void send(String rfqNo) {
		log.info("==================== Send Pr START ====================");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Agent agt = ((ApiUserDetailsImpl) authentication.getPrincipal()).getAgent();

		PrMap prMap = prMapMapper.findByRfqNo(rfqNo);
		if (prMap == null || prMap.getErpPrNo() != null)
			return;

		// 사전입찰 진행된 구매요청정보 송신시작
		String prNo = prMap.getE4uPrNo();
		Pr pr = prService.getPr(prNo);

		PrDto.Create cre = modelMapper.map(pr, PrDto.Create.class);

		cre.setItemList(new ArrayList<PrItemDto.Create>());
		for (PrItem prItem : pr.getItemList()) {
			PrItemDto.Create creItem = modelMapper.map(prItem, PrItemDto.Create.class);
			cre.getItemList().add(creItem);
			creItem.setSrvList(new ArrayList<PrSrvDto.Create>());
			for (PrSrv prSrv : prItem.getSrvList()) {
				PrSrvDto.Create creSrv = modelMapper.map(prSrv, PrSrvDto.Create.class);
				creItem.getSrvList().add(creSrv);
			}

		} // for

		HttpHeaders jsonPartHeader = new HttpHeaders();
		jsonPartHeader.setContentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());
		HttpEntity<PrDto.Create> jsonPart = new HttpEntity<PrDto.Create>(cre, jsonPartHeader);

		MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<String, Object>();
		multiValueMap.add("data", jsonPart);

		File tmpDir = enioFileHandler.createTempDirectory();
		tmpDir.mkdirs();

		if (pr.getFileList().size() > 0) {
			FtpFileDto.Retrieve r = null;

			for (PrFile prFile : pr.getFileList()) {
				r = new FtpFileDto.Retrieve();
				r.setRemoteFilePath(prFile.getSvrFilePath());
				r.setRemoteFileName(prFile.getSvrFileNm());
				r.setFile(new File(tmpDir, prFile.getCliFileNm()));
				ftpService.retrieve(r);
				multiValueMap.add("files", new EnioFileSystemResource(r.getFile(), r.getFile().getName()));
			} // for
		}
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.setAccept(Collections.singletonList(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType()));

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(
				multiValueMap, headers);

		headers.setContentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());

		String url = agt.getConnUrl() + prAgentUriPath;
		log.info("sendUrl : {}", url);
		PrDto.Response res = restTemplate.postForObject(url, requestEntity, PrDto.Response.class);
		if (RsltCd.SUC0000.name().equals(res.getRsltCd())) {
			prMapMapper.delPrMap(prNo);
			prMapMapper.add(prMap);
		}
		log.info("rsltCd : {}", res.getRsltCd());
		log.info("rsltMsg : {}", res.getRsltMsg());
		log.info("==================== Send Pr End ====================");
	}
}
