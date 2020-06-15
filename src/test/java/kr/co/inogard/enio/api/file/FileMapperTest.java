package kr.co.inogard.enio.api.file;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import kr.co.inogard.enio.api.Application;
import kr.co.inogard.enio.api.domain.file.CmmFile;
import kr.co.inogard.enio.api.mappers.file.FileMapper;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@Slf4j
public class FileMapperTest {

	@Autowired
	private FileMapper fileMapper;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Test
	public void createFileTest() {
		CmmFile file = modelMapper.map(FileTestFixture.getFileCreateFixture(), CmmFile.class);
		fileMapper.add(file);
		
		log.debug("file : {}", file);
		
	}
	
}
