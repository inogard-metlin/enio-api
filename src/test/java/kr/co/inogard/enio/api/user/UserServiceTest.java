package kr.co.inogard.enio.api.user;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import kr.co.inogard.enio.api.Application;
import kr.co.inogard.enio.api.domain.user.User;
import kr.co.inogard.enio.api.service.user.UserService;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@Slf4j
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@Test
	public void findByUserCdTest() {
		User user = userService.getByUserCd("A019M00001");
		log.debug("userCd : {}", user.getUserCd());
		assertThat("A019M00001", is(user.getUserCd()));
	}
	
}
