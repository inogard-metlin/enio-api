package kr.co.inogard.enio.api.commons.interceptor;

import java.util.Collection;
import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class HttpInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
      throws Exception {
    log.info("============================== preHandle START ==============================");
    log.info("Request URI : {}", request.getRequestURI());

    Enumeration<String> headerNames = request.getHeaderNames();
    while (headerNames.hasMoreElements()) {
      String key = headerNames.nextElement();
      String value = request.getHeader(key);
      log.info("RequestHeaders Data ==>  {} : {}", key, value);
    }

    Enumeration<String> paramNames = request.getParameterNames();
    while (paramNames.hasMoreElements()) {
      String key = (String) paramNames.nextElement();
      String value = request.getParameter(key);
      log.info("RequestParameter Data ==>  {} : {}", key, value);
    }

    log.info("============================== preHandle END ==============================");
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
    log.info("============================== postHandle START ==============================");
    log.info("Status code : {}", response.getStatus());

    Collection<String> headerNames = response.getHeaderNames();
    for (String key : headerNames) {
      String value = response.getHeader(key);
      log.info("ResponseHeaders Data ==> {} : {}", key, value);
    }
    log.info("============================== postHandle END ==============================");
  }

}
