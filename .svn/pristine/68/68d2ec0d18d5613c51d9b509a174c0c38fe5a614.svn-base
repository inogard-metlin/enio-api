package kr.co.inogard.enio.api.user;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import kr.co.inogard.enio.api.Application;
import kr.co.inogard.enio.api.domain.user.User;
import kr.co.inogard.enio.api.domain.user.UserDto;
import kr.co.inogard.enio.api.service.user.UserService;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@Slf4j
public class UserControllerTest {
	
	MockMvc mockMvc;

	@Autowired
	WebApplicationContext wac;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ModelMapper modelMapper;
}
