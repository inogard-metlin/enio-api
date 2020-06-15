package kr.co.inogard.enio.api.pr.srv;

import java.text.ParseException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import kr.co.inogard.enio.api.Application;
import kr.co.inogard.enio.api.domain.file.CmmFile;
import kr.co.inogard.enio.api.domain.pr.PrDto;
import kr.co.inogard.enio.api.domain.pr.PrFile;
import kr.co.inogard.enio.api.domain.pr.PrFileDto;
import kr.co.inogard.enio.api.domain.pr.PrItem;
import kr.co.inogard.enio.api.domain.pr.PrSrv;
import kr.co.inogard.enio.api.domain.pr.PrSrvDto;
import kr.co.inogard.enio.api.mappers.file.FileMapper;
import kr.co.inogard.enio.api.mappers.pr.PrFileMapper;
import kr.co.inogard.enio.api.mappers.pr.PrSrvMapper;
import kr.co.inogard.enio.api.pr.PrTestFixture;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@Slf4j
public class PrSrvMapperTest {

	@Autowired
	private PrSrvMapper prSrvMapper;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PrTestFixture prTestFixture;
	
	@Test
	public void createPrSrvTest() throws ParseException {
		PrDto.Create create = prTestFixture.getPrCreateFixture();
		List<PrSrv> srvList = modelMapper.map(create.getItemList().get(0).getSrvList(), new TypeToken<List<PrSrv>>() {}.getType());
		for (PrSrv prSrv : srvList) {
			prSrvMapper.add(prSrv);
		}
	}
	
}
