package kr.co.inogard.enio.api.domain.pr;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import kr.co.inogard.enio.api.commons.validator.MaxByteLength;
import lombok.Data;

public class PrFileDto {

  @Data
  public static class Create {
    @NotBlank
    @MaxByteLength(30)
    private String erpPrNo;

    @NotBlank
    @Size(min = 20, max = 20)
    private String fileNo;
  }

}
