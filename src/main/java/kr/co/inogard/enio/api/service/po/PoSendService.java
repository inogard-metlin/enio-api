package kr.co.inogard.enio.api.service.po;

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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import kr.co.inogard.enio.api.commons.EnioFileSystemResource;
import kr.co.inogard.enio.api.commons.constant.EnioMediaType;
import kr.co.inogard.enio.api.commons.handler.EnioFileHandler;
import kr.co.inogard.enio.api.commons.handler.EnioValidateHandler;
import kr.co.inogard.enio.api.domain.agent.Agent;
import kr.co.inogard.enio.api.domain.ftp.FtpFileDto;
import kr.co.inogard.enio.api.domain.po.Po;
import kr.co.inogard.enio.api.domain.po.PoDto;
import kr.co.inogard.enio.api.domain.po.PoFile;
import kr.co.inogard.enio.api.domain.po.PoFileDto;
import kr.co.inogard.enio.api.domain.po.PoItem;
import kr.co.inogard.enio.api.domain.po.PoItemDto;
import kr.co.inogard.enio.api.domain.po.PoSrv;
import kr.co.inogard.enio.api.domain.quot.QuotDto;
import kr.co.inogard.enio.api.domain.quot.QuotFile;
import kr.co.inogard.enio.api.mappers.po.PoFileMapper;
import kr.co.inogard.enio.api.mappers.po.PoItemMapper;
import kr.co.inogard.enio.api.mappers.po.PoMapper;
import kr.co.inogard.enio.api.mappers.po.PoSrvMapper;
import kr.co.inogard.enio.api.security.ApiUserDetailsImpl;
import kr.co.inogard.enio.api.service.ftp.FtpService;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@Slf4j
public class PoSendService {

	@Autowired
	private PoMapper poMapper;

	@Autowired
	private PoItemMapper poItemMapper;

	@Autowired
	private PoSrvMapper poSrvMapper;

	@Autowired
	private PoFileMapper poFileMapper;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private EnioValidateHandler enioValidateHandler;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private EnioFileHandler enioFileHandler;

	@Autowired
	private FtpService ftpService;	
	
	@Value("${enio.agent.context-path}/${enio.agent.version}${enio.agent.uri-path.po}")
	private String poAgentUriPath;

	public Po createPo(String poNo) {
		log.info("==================== Send Po START ====================");
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Agent agt = ((ApiUserDetailsImpl) authentication.getPrincipal()).getAgent();

		Po po = poMapper.findByPoNo(poNo);
		PoDto.Create poDto = modelMapper.map(po, PoDto.Create.class);

		List<PoItem> poItemList = poItemMapper.findPoItemByPoNo(poNo);
		List<PoSrv> poSrvList = null;
		for (PoItem xPoItem : poItemList) {
			poSrvList = poSrvMapper.findPoSrvByPoNo(xPoItem);
			xPoItem.setSrvList(poSrvList);
		}

		List<PoItemDto.Create> poItemDtoList = modelMapper.map(poItemList, new TypeToken<List<PoItemDto.Create>>() {}.getType());
		poDto.setPoItem(poItemDtoList);

		List<PoFile> poFileList = poFileMapper.findPoFileByPoNo(poNo);
		
		enioValidateHandler.validate(poDto);
		
		log.debug("## CREATE PO ## : {}", poDto.getPoNo());

		HttpHeaders jsonPartHeader = new HttpHeaders();
		jsonPartHeader.setContentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());
		HttpEntity<PoDto.Create> jsonPart = new HttpEntity<PoDto.Create>(poDto, jsonPartHeader);

		MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<String, Object>();
		multiValueMap.add("data", jsonPart);

		File tmpDir = enioFileHandler.createTempDirectory();
		tmpDir.mkdirs();

		if (poFileList.size() > 0) {
			FtpFileDto.Retrieve r = null;

			for (PoFile poFile : poFileList) {
				r = new FtpFileDto.Retrieve();
				r.setRemoteFilePath(poFile.getSvrFilePath());
				r.setRemoteFileName(poFile.getSvrFileNm());
				r.setFile(new File(tmpDir, poFile.getCliFileNm()));
				ftpService.retrieve(r);
				multiValueMap.add("files", new EnioFileSystemResource(r.getFile(), r.getFile().getName()));
			}
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		headers.setAccept(Collections.singletonList(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType()));

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<MultiValueMap<String, Object>>(multiValueMap, headers);

		headers.setContentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());		
		
		String url = agt.getConnUrl() + poAgentUriPath;
		PoDto.Response res = null;

		try {
			res = restTemplate.postForObject(url, requestEntity, PoDto.Response.class);
		} catch (HttpClientErrorException e) {
			e.getResponseBodyAsString();
			log.debug("error : {}", e.getResponseBodyAsString());
		}
		
		log.debug("res.getRsltCd() : {}", res.getRsltCd());
		log.debug("res.getRsltMsg() : {}", res.getRsltMsg());
		log.info("enio 데이터 전송완료");
		
		log.info("==================== Send Po End ====================");
		
		return po;
	}

}
