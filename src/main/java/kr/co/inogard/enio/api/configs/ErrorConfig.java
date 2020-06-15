package kr.co.inogard.enio.api.configs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Configuration
public class ErrorConfig {

  @Bean
  public EmbeddedServletContainerCustomizer containerCustomizer() {
    return new EmbeddedServletContainerCustomizer() {
      @Override
      public void customize(ConfigurableEmbeddedServletContainer container) {
        container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/error/403"));
        container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/error/404"));
        container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/error/500"));
      }
    };
  }

  @Bean
  public BasicErrorController errorController(ErrorAttributes errorAttributes,
      ServerProperties serverProperties) {

    return new BasicErrorController(errorAttributes, serverProperties.getError()) {

      @RequestMapping(value = "/403", produces = "text/html", method = RequestMethod.GET)
      public String accessDeniedPage() {
        return "errors/403";
      }

      @RequestMapping(value = "/404", produces = "text/html", method = RequestMethod.GET)
      public String notFound() {
        return "errors/404";
      }

      @RequestMapping(value = "/500", produces = "text/html", method = RequestMethod.GET)
      public String internalServerError() {
        return "errors/404";
      }

      @Override
      @RequestMapping(method = RequestMethod.GET)
      public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = super.errorHtml(request, response);
        mv.setViewName("errors/" + mv.getViewName());
        return mv;
      }
    };
  }
}