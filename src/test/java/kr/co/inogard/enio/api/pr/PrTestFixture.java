package kr.co.inogard.enio.api.pr;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import kr.co.inogard.enio.api.Application;
import kr.co.inogard.enio.api.commons.constant.IOFlag;
import kr.co.inogard.enio.api.commons.constant.PrTypeCd;
import kr.co.inogard.enio.api.commons.constant.TaxTypeCd;
import kr.co.inogard.enio.api.commons.domain.ErrorResponse.FieldError;
import kr.co.inogard.enio.api.commons.exception.EnioRunTimeException;
import kr.co.inogard.enio.api.commons.handler.EnioValidateHandler;
import kr.co.inogard.enio.api.domain.pr.PrDto;
import kr.co.inogard.enio.api.domain.pr.PrItemDto;
import kr.co.inogard.enio.api.domain.pr.PrSrvDto;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@Slf4j
@Component
public class PrTestFixture {
	
	@Autowired
	private EnioValidateHandler enioValidateHandler; 
	
	@Test
	public void validTest() throws ParseException {
		enioValidateHandler.validate(getPrCreateFixture());
	}
	
	public PrDto.Create getPrCreateFixture() throws ParseException {
		SimpleDateFormat formatterYMD = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat formatterYMDHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");

		PrDto.Create createDto = new PrDto.Create();
		createDto.setPrNo("");
		createDto.setTtl("구매요청 테스트");
		createDto.setPrTypecd(PrTypeCd.N.name());
		createDto.setTaxTypecd(TaxTypeCd.A1000.name());
		createDto.setIoFlag(IOFlag.D.name());
		createDto.setGmChrgrCd("S003700001");
		createDto.setPrChrgrCd("S003700002");
		createDto.setGrChrgrCd("S003700002");
		createDto.setChkChrgrCd("S003700002");
		createDto.setDlvFrYmd(null);
		createDto.setDlvToYmd(null);
		createDto.setReqUsrNm("실수요자");
		createDto.setReqUsrTel("02-2121-2121");
		createDto.setPrRmrk("구매요청비고");
		createDto.setDlvLoc("납품장소");
		createDto.setDlvReqYmd(formatterYMD.parse("20170918"));
		createDto.setSpotDscrYn("N");
		createDto.setSpotDscrDt(formatterYMD.parse("2017091817"));
		createDto.setSpotDscrUsrnm(null);
		createDto.setSpotDscrUsrtel(null);
		createDto.setSpotDscrLoc(null);
		createDto.setPrebidYn("N");

		PrItemDto.Create itemCreateDto = new PrItemDto.Create();
		itemCreateDto.setPrNo("01234567890123456789");
		itemCreateDto.setItemSeq("00001");
		itemCreateDto.setItemNm("물품명");
		itemCreateDto.setItemCd("12345678901234567890");
		itemCreateDto.setClsCd("1234567890");
		itemCreateDto.setSizeNm("규격");
		itemCreateDto.setModelNm("모델명");
		itemCreateDto.setUnitCd("set");
		itemCreateDto.setQty(new BigDecimal("1901211231.12"));
		itemCreateDto.setPlanUnitPrc(new BigDecimal("1000000"));
		itemCreateDto.setMkCompNm("제조사");
		
		PrSrvDto.Create srvCreateDto = new PrSrvDto.Create();
		srvCreateDto.setPrNo("01234567890123456789");
		srvCreateDto.setItemSeq("00001");
		srvCreateDto.setSrvNm("서비스명");
		srvCreateDto.setSrvCd("12345");
		srvCreateDto.setUnitCd("set");
		srvCreateDto.setQty(new BigDecimal("1"));
		
		List<PrItemDto.Create> itemList = new ArrayList<PrItemDto.Create>();
		itemList.add(itemCreateDto);
		List<PrSrvDto.Create> srvList = new ArrayList<PrSrvDto.Create>();
		srvList.add(srvCreateDto);
		itemList.get(0).setSrvList(srvList);
		createDto.setItemList(itemList);
		
		enioValidateHandler.validate(createDto);
		return createDto;
	}
}
