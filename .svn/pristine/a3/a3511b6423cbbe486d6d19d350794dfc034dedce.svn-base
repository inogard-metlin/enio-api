package kr.co.inogard.enio.api.po;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import kr.co.inogard.enio.api.Application;
import kr.co.inogard.enio.api.commons.handler.EnioValidateHandler;
import kr.co.inogard.enio.api.domain.po.Po;
import kr.co.inogard.enio.api.domain.po.PoDto;
import kr.co.inogard.enio.api.service.po.PoSendService;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@Slf4j
public class PoServiceTest {

	@Autowired
	private PoSendService poService;
	
//	@Autowired
//	private PoItemService poItemService;
//	
//	@Autowired
//	private PoSrvService poSrvService;
//	
//	@Autowired
//	private PoFileService poFileService;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	RestTemplate restTemplate;	
	
	@Autowired
	private EnioValidateHandler enioValidateHandler;
	
	@Test
	public void createPoTest() {
//		String poNo = "A0190302017053000001";
		String poNo = "A018K302016052500001";
		
		Po po = poService.createPo(poNo);
		log.debug("## SUCCESS CREATE PO ## : {}", po.getPoNo());
	}
	
//	@Test
//	public void findPoItemByPoNo() {
//		String poNo = "A0190302017053000001";
//		
//		List<PoItem> poItemList = poItemService.findPoItemByPoNo(poNo);
//		
//		log.debug("findPoItemByPoNo() - poItemList : "  + poItemList.size());
//		log.debug("findPoItemByPoNo() - poItemList : "  + poItemList.size());
//		log.debug("findPoItemByPoNo() - poItemList : "  + poItemList.size());
//	}
	
//	@Test
//	public void findPoSrvByPoNo() {
//		String poNo = "A0190302017053000001";
//		
//		List<PoItem> poItemList = poItemService.findPoItemByPoNo(poNo);
//		
//		List<PoSrv> poSrvList = null;
//		for(PoItem xPoItem : poItemList) {
//			poSrvList = poSrvService.findPoSrvByPoNo(xPoItem);
//			log.debug("===> poSrvList.size() : " +  poSrvList.size());
//			log.debug("===> poSrvList.size() : " +  poSrvList.size());
//			log.debug("===> poSrvList.size() : " +  poSrvList.size());
//			
//			xPoItem.setSrvList(poSrvList);
//		}
//	}
	
//	@Test
//	public void findPoFileByPoNo() {
//		String poNo = "A0190302017053000001";
//		
//		List<PoFile> poFileList = poFileService.findPoFileByPoNo(poNo);	
//
//		log.debug("findPoFileByPoNo() - poFileList : "  + poFileList.size());
//		log.debug("findPoFileByPoNo() - poFileList : "  + poFileList.size());
//		log.debug("findPoFileByPoNo() - poFileList : "  + poFileList.size());
//	}
	
	@Test
	public void validTest() throws Exception{
		PoDto.Create createDto = PoTestFixture.getPoCreateFixture();
//		createDto.setPoNo("");
		enioValidateHandler.validate(createDto);
	}
}
