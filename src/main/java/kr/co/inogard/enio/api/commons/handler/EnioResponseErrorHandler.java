package kr.co.inogard.enio.api.commons.handler;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.UnknownHttpStatusCodeException;
import kr.co.inogard.enio.api.commons.constant.EnioMediaType;
import kr.co.inogard.enio.api.commons.util.CryptoUtil;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EnioResponseErrorHandler extends DefaultResponseErrorHandler {

  @Autowired
  private CryptoUtil cryptoUtil;

  private HttpStatus getHttpStatusCode(ClientHttpResponse response) throws IOException {
    HttpStatus statusCode;
    try {
      statusCode = response.getStatusCode();
    } catch (IllegalArgumentException ex) {
      throw new UnknownHttpStatusCodeException(response.getRawStatusCode(),
          response.getStatusText(), response.getHeaders(), getResponseBody(response),
          getCharset(response));
    }
    return statusCode;
  }

  @Override
  public void handleError(ClientHttpResponse response) throws IOException {
    log.debug("============================== Handle Error START ==============================");
    try {
      HttpStatus statusCode = getHttpStatusCode(response);
      switch (statusCode.series()) {
        case CLIENT_ERROR:
          throw new HttpClientErrorException(statusCode, response.getStatusText(),
              response.getHeaders(), getResponseBody(response), getCharset(response));
        case SERVER_ERROR:
          throw new HttpServerErrorException(statusCode, response.getStatusText(),
              response.getHeaders(), getResponseBody(response), getCharset(response));
        default:
          throw new RestClientException("Unknown status code [" + statusCode + "]");
      }
    } finally {
      log.debug("============================== Handle Error END ==============================");
    }
  }

  private byte[] getResponseBody(ClientHttpResponse response) {
    try {
      InputStream responseBody = response.getBody();
      if (responseBody != null) {
        if (EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType()
            .isCompatibleWith(response.getHeaders().getContentType())) {
          String responseBodyStr =
              new String(FileCopyUtils.copyToByteArray(responseBody), getCharset(response).name());
          log.debug("responseBodyStr : {}", responseBodyStr);
          String decryptString =
              cryptoUtil.decryptString(cryptoUtil.getCryptoKey(), responseBodyStr);
          log.debug("decryptString : {}", decryptString);
          return decryptString.getBytes();
        } else {
          return FileCopyUtils.copyToByteArray(responseBody);
        }
      }
    } catch (IOException ex) {
      log.error(ex.getMessage(), ex);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
    }
    return new byte[0];
  }

  private Charset getCharset(ClientHttpResponse response) {
    HttpHeaders headers = response.getHeaders();
    MediaType contentType = headers.getContentType();
    return contentType != null ? contentType.getCharSet() : null;
  }

}
