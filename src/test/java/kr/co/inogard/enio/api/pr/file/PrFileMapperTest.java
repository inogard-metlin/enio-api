package kr.co.inogard.enio.api.pr.file;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import kr.co.inogard.enio.api.Application;
import kr.co.inogard.enio.api.domain.pr.PrFile;
import kr.co.inogard.enio.api.domain.pr.PrFileDto;
import kr.co.inogard.enio.api.mappers.pr.PrFileMapper;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@Slf4j
public class PrFileMapperTest {

	@Autowired
	private PrFileMapper prFileMapper;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Test
	public void createPrFileTest() {
		PrFileDto.Create create = new PrFileDto.Create();
		create.setErpPrNo("12345678901234567890");
		create.setFileNo("12345678901234567890");
		PrFile prFile = modelMapper.map(create, PrFile.class);
		prFileMapper.add(prFile);
		log.debug("prFile : {}", prFile);
	}
	
}
