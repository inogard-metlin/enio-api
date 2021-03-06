package kr.co.inogard.enio.api.domain.rfq;

import java.math.BigDecimal;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import kr.co.inogard.enio.api.commons.validator.MaxByteLength;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

public class RfqSrvDto {

  @Data
  public static class Create {
    @NotBlank
    @Size(min = 20)
    @MaxByteLength(20)
    private String rfqNo;

    @NotBlank
    @Size(min = 1)
    @MaxByteLength(5)
    private String itemSeq;

    @NotBlank
    @Size(min = 1)
    @MaxByteLength(5)
    private String srvSeq;

    @Size(min = 20)
    @MaxByteLength(20)
    private String prNo;

    @Size(min = 1)
    @MaxByteLength(5)
    private String prItemSeq;

    @Size(min = 1)
    @MaxByteLength(5)
    private String prSrvSeq;

    @MaxByteLength(150)
    private String srvNm;

    @MaxByteLength(5)
    private String srvCd;

    @MaxByteLength(5)
    private String unitCd;

    @Digits(fraction = 2, integer = 12)
    private BigDecimal qty;
  }

  @Data
  public static class Response {
    private String rfqNo;
    private String itemSeq;
    private String srvSeq;
    private String prNo;
    private String prItemSeq;
    private String prSrvSeq;
    private String srvNm;
    private String srvCd;
    private String unitCd;
    private BigDecimal qty;
  }
}
