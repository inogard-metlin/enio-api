package kr.co.inogard.enio.api.service.quot;

import java.io.File;
import java.util.Collections;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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
import kr.co.inogard.enio.api.commons.handler.EnioFileHandler;
import kr.co.inogard.enio.api.commons.handler.EnioValidateHandler;
import kr.co.inogard.enio.api.domain.agent.Agent;
import kr.co.inogard.enio.api.domain.ftp.FtpFileDto;
import kr.co.inogard.enio.api.domain.quot.Quot;
import kr.co.inogard.enio.api.domain.quot.QuotDto;
import kr.co.inogard.enio.api.domain.quot.QuotFile;
import kr.co.inogard.enio.api.domain.quot.QuotItem;
import kr.co.inogard.enio.api.domain.quot.QuotItemDto;
import kr.co.inogard.enio.api.domain.quot.QuotSrv;
import kr.co.inogard.enio.api.mappers.quot.QuotFileMapper;
import kr.co.inogard.enio.api.mappers.quot.QuotItemMapper;
import kr.co.inogard.enio.api.mappers.quot.QuotMapper;
import kr.co.inogard.enio.api.mappers.quot.QuotSrvMapper;
import kr.co.inogard.enio.api.security.ApiUserDetailsImpl;
import kr.co.inogard.enio.api.service.ftp.FtpService;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class QuotSendService {

	@Autowired
	private QuotMapper quotMapper;

	@Autowired
	private QuotItemMapper quotItemMapper;

	@Autowired
	private QuotSrvMapper quotSrvMapper;

	@Autowired
	private QuotFileMapper quotFileMapper;

	@Autowired
	EnioValidateHandler enioValidateHandler;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	private EnioFileHandler enioFileHandler;

	@Autowired
	private FtpService ftpService;

	@Value("${enio.agent.context-path}/${enio.agent.version}${enio.agent.uri-path.bid.quot}")
	private String quotAgentUriPath;

	@Autowired
	private RestTemplate restTemplate;

	public void send(String rfqNo) {
		log.info("==================== Send Quot START ====================");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Agent agt = ((ApiUserDetailsImpl) authentication.getPrincipal()).getAgent();

		List<Quot> quotList = quotMapper.findAllByRfqNo(rfqNo);

		for (Quot xQuot : quotList) {
			QuotDto.Create cre = modelMapper.map(xQuot, QuotDto.Create.class);
			List<QuotItem> itemList = null;
			List<QuotSrv> srvList = null;
			List<QuotFile> fileList = null;

			itemList = quotItemMapper.findAllByQuot(xQuot);

			for (QuotItem xQuotItem : itemList) {
				srvList = quotSrvMapper.findAllByQuotItem(xQuotItem);
				xQuotItem.setQuotSrvList(srvList);
			}
			List<QuotItemDto> quotItemList = modelMapper.map(itemList, new TypeToken<List<QuotItemDto>>() {
			}.getType());
			cre.setQuotItemList(quotItemList);

			fileList = quotFileMapper.findAllByQuot(xQuot);

			enioValidateHandler.validate(cre);

			HttpHeaders jsonPartHeader = new HttpHeaders();
			jsonPartHeader.setContentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());
			HttpEntity<QuotDto.Create> jsonPart = new HttpEntity<QuotDto.Create>(cre, jsonPartHeader);

			MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<String, Object>();
			multiValueMap.add("data", jsonPart);

			File tmpDir = enioFileHandler.createTempDirectory();
			tmpDir.mkdirs();

			if (fileList.size() > 0) {
				FtpFileDto.Retrieve r = null;

				for (QuotFile quotFile : fileList) {
					r = new FtpFileDto.Retrieve();
					r.setRemoteFilePath(quotFile.getSvrFilePath());
					r.setRemoteFileName(quotFile.getSvrFileNm());
					r.setFile(new File(tmpDir, quotFile.getCliFileNm()));
					ftpService.retrieve(r);
					multiValueMap.add("files", new EnioFileSystemResource(r.getFile(), r.getFile().getName()));
				}
			}
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);
			headers.setAccept(Collections.singletonList(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType()));

			HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(multiValueMap, headers);

			headers.setContentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());

			String url = agt.getConnUrl() + quotAgentUriPath;
			log.info("sendUrl : {}", url);
			QuotDto.Response res = restTemplate.postForObject(url, requestEntity, QuotDto.Response.class);
			log.debug("rsltCd : {}", res.getRsltCd());
			log.debug("rsltMsg : {}", res.getRsltMsg());
			log.info("==================== Send Quot End ====================");
		}
	}

}
