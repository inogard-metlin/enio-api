package kr.co.inogard.enio.api.pr;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.inogard.enio.api.Application;
import kr.co.inogard.enio.api.domain.pr.Pr;
import kr.co.inogard.enio.api.mappers.pr.PrMapper;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Slf4j
public class PrMapperTest {
	
	@Autowired
	private PrMapper prMapper;
	
	@Autowired
    private ModelMapper modelMapper;
	
	@Autowired
	PrTestFixture prTestFixture;
	
	@Test
	public void createPrTest() throws ParseException {
		Pr pr = modelMapper.map(prTestFixture.getPrCreateFixture(), Pr.class);
		pr.setSubOrgCd(pr.getOrgCd() + "01");
		prMapper.add(pr);
		log.debug("Create Pr : {}", pr);
	}
	
}
