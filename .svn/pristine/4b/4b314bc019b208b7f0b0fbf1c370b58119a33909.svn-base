package kr.co.inogard.enio.api;

import org.modelmapper.ModelMapper;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import kr.co.inogard.enio.api.service.ftp.FtpConnectionAdvice;
import kr.co.inogard.enio.api.service.ftp.FtpService;
import kr.co.inogard.enio.api.service.ftp.FtpServiceImpl;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class Application extends SpringBootServletInitializer {

  @Override
  protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
    return application.sources(Application.class);
  }

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Autowired
  FtpConnectionAdvice ftpConnectionAdvice;

  @Bean
  public FtpService ftpService() {
    ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
    proxyFactoryBean.setTarget(new FtpServiceImpl());
    proxyFactoryBean.addAdvisor(new DefaultPointcutAdvisor(ftpConnectionAdvice));
    return (FtpService) proxyFactoryBean.getObject();
  }

  @Component
  public static class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${enio.welcome.message}")
    private String welcomeMessage;

    @Autowired
    private Environment environment;

    @Override
    public void onApplicationEvent(final ApplicationReadyEvent event) {
      log.info("welcomeMessage : {}", welcomeMessage);
      log.info("activeProfiles : {}", (Object) environment.getActiveProfiles());
      log.info("Application startup complete!");
    }
  }
}
