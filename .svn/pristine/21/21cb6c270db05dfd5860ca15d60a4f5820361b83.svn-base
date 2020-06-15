package kr.co.inogard.enio.api.commons.converter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.util.CollectionUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.inogard.enio.api.commons.constant.EnioMediaType;
import kr.co.inogard.enio.api.commons.util.CryptoUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnioJsonHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

  @Autowired
  private CryptoUtil cryptoUtil;

  private ObjectMapper objectMapper;

  public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

  public EnioJsonHttpMessageConverter() {
    super(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());
    this.objectMapper = Jackson2ObjectMapperBuilder.json().build();
  }

  @Override
  public boolean canRead(Class<?> clazz, MediaType mediaType) {
    return canRead(mediaType);
  }

  @Override
  public boolean canWrite(Class<?> clazz, MediaType mediaType) {
    return canWrite(mediaType);
  }

  @Override
  protected boolean supports(Class<?> clazz) {
    throw new UnsupportedOperationException();
  }

  @Override
  protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage)
      throws IOException, HttpMessageNotReadableException {
    log.info("==================== Read Internal START ====================");

    Object obj = null;
    try {
      String decryptedEnioJsonStr = cryptoUtil.decryptString(cryptoUtil.getCryptoKey(),
          getStringFromInputStream(inputMessage.getBody()));
      log.debug("decryptedEnioJsonStr : {}", decryptedEnioJsonStr);
      obj = objectMapper.readValue(decryptedEnioJsonStr, clazz);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new HttpMessageNotReadableException(e.getMessage(), e);
    }
    log.info("==================== Read Internal END ====================");
    return obj;
  }

  @Override
  protected void writeInternal(Object t, HttpOutputMessage outputMessage)
      throws IOException, HttpMessageNotWritableException {
    log.info("==================== Write Internal START ====================");
    try {
      if (CollectionUtils.isEmpty(outputMessage.getHeaders().get("WWW-Authenticate"))) {
        String encryptedEnioJsonStr =
            cryptoUtil.encryptString(cryptoUtil.getCryptoKey(), objectMapper.writeValueAsString(t));

        log.debug("encryptedEnioJsonStr : {}", encryptedEnioJsonStr);
        outputMessage.getBody().write(encryptedEnioJsonStr.getBytes());
      }
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new HttpMessageNotWritableException(e.getMessage(), e);
    }
    log.info("==================== Write Internal END ====================");
  }

  private String getStringFromInputStream(InputStream is) throws Exception {
    BufferedReader br = null;
    StringBuilder sb = new StringBuilder();

    String line;
    try {
      br = new BufferedReader(new InputStreamReader(is));
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }
      return sb.toString();
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
