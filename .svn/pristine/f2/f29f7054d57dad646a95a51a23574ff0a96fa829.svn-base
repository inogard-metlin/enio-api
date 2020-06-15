package kr.co.inogard.enio.api.ftp;

import java.io.File;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import kr.co.inogard.enio.api.Application;
import kr.co.inogard.enio.api.commons.handler.EnioFileHandler;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@Slf4j
public class EnioFileHandlerTest {

	@Autowired
	private EnioFileHandler enioFileHandler;
	
	@Test
	public void ctmTempFileCreateTest() {
		File ctmTempDir = enioFileHandler.createTempDirectory();
	}
	
}
