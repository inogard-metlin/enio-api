package kr.co.inogard.enio.api.domain.agent;

import java.util.Date;
import org.springframework.security.crypto.codec.Base64;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Agent {
  private String agtCd;
  private String licenceKey;
  private String connUrl;
  private String agtChrgrNm;
  private String agtChrgrTel;
  private String agtChrgrSms;
  private String agtChrgrEmail;
  private String installOrgNm;
  private Date regDt;

  public String getRawLicenceKey() {
    String passwd = null;
    try {
      String base64Encoded = this.licenceKey;
      passwd = new String(Base64.decode(base64Encoded.getBytes()), "UTF-8");
    } catch (Exception e) { //
    }
    return passwd;
  }
}
