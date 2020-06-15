package kr.co.inogard.enio.api.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import kr.co.inogard.enio.api.commons.filter.LoggingFilter;
import kr.co.inogard.enio.api.commons.filter.SiteMeshFilter;

@Configuration
public class FilterConfig {
  @Autowired
  private LoggingFilter loggingFilter;

  @Bean
  public FilterRegistrationBean getFilterRegistrationBean() {
    FilterRegistrationBean registrationBean = new FilterRegistrationBean(loggingFilter);
    registrationBean.addUrlPatterns("/api/*");
    registrationBean.setOrder(1);

    return registrationBean;
  }

  @Bean
  public FilterRegistrationBean siteMeshFilter() {
    FilterRegistrationBean registrationBean = new FilterRegistrationBean(new SiteMeshFilter());
    return registrationBean;
  }
}
