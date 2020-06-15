package kr.co.inogard.enio.api.cmm;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import kr.co.inogard.enio.api.Application;
import kr.co.inogard.enio.api.domain.pr.Pr;
import kr.co.inogard.enio.api.domain.pr.PrDto;
import kr.co.inogard.enio.api.service.pr.PrService;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@Slf4j
public class PasswordEncoderTest {
	
	@Autowired
    private PasswordEncoder passwordEncoder;
	
	@Test
	public void passwordEncoderTest() throws ParseException {
		log.debug("Encoded password : {}", passwordEncoder.encode("a123456A"));	
	}
	
}
