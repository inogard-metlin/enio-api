package kr.co.inogard.enio.api.commons.domain;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponse {

  private String rsltCd;
  private String rsltMsg;
  private List<FieldError> errors;

  @Data
  @JsonInclude(JsonInclude.Include.NON_EMPTY)
  public static class FieldError {
    private String code;
    private String field;
    private String rejectedValue;
    private String defaultMessage;
  }

}
