package kr.co.inogard.enio.api.commons.constant;

import org.springframework.http.MediaType;
import lombok.Getter;

@Getter
public enum EnioMediaType {
  APPLICATION_ENIO_JSON("application/enio-json"), APPLICATION_ENIO_JSON_UTF8(
      EnioMediaType.APPLICATION_ENIO_JSON.getValue() + ";charset=UTF-8");

  private String value;

  private EnioMediaType(String value) {
    this.value = value;
  }

  public MediaType getMediaType() {
    return MediaType.valueOf(this.getValue());
  }

}
