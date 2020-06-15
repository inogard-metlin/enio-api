package kr.co.inogard.enio.api.domain.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Component;
import lombok.Getter;

@Getter
@Component
public class ApiConfig {

  @Value("${enio.api.code}")
  private String apiCd;

  @Value("${enio.api.licence-key}")
  private String licenceKey;

  public String getRawLicenceKey() {
    String passwd = null;
    try {
      String base64Encoded = this.licenceKey;
      passwd = new String(Base64.decode(base64Encoded.getBytes()), "UTF-8");
    } catch (Exception e) {
    }
    return passwd;
  }
}
