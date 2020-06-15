package kr.co.inogard.enio.api.commons.handler;

import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import lombok.extern.slf4j.Slf4j;

//@ControllerAdvice
@Slf4j
public class HttpResponseBodyAdvice implements ResponseBodyAdvice<Object> {

	@Override
	public boolean supports(MethodParameter returnType, Class converterType) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class selectedConverterType,
			ServerHttpRequest request, ServerHttpResponse response) {
		log.debug("============================== beforeBodyWrite START ==============================");
		log.debug("request.getURI() : " + request.getURI());
		log.debug("body : " + body);
		log.debug("============================== beforeBodyWrite END ==============================");
		return body;
	}
}
