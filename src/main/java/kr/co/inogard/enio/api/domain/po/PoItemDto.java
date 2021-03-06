package kr.co.inogard.enio.api.domain.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.inogard.enio.api.commons.validator.MaxByteLength;
import lombok.Data;

@Data
public class PoItemDto {

  @Data
  public static class Create {
    @NotBlank
    @Size(min = 20)
    @MaxByteLength(20)
    private String poNo;

    @NotBlank
    @Size(min = 5)
    @MaxByteLength(5)
    private String itemSeq;

    @Size(min = 20)
    @MaxByteLength(20)
    private String prNo;

    @Size(min = 5)
    @MaxByteLength(5)
    private String prItemSeq;

    @MaxByteLength(150)
    private String itemNm;

    @Size(min = 20)
    @MaxByteLength(20)
    private String itemCd;

    @MaxByteLength(10)
    private String clsCd;

    @MaxByteLength(120)
    private String sizeNm;

    @MaxByteLength(150)
    private String modelNm;

    @MaxByteLength(120)
    private String mkCompNm;

    private String dlvLoc;
    private Date dlvReqYmd;

    @MaxByteLength(6)
    private String unitCd;

    @Digits(fraction = 2, integer = 12)
    private BigDecimal qty;

    @Digits(fraction = 4, integer = 15)
    private BigDecimal unitPrc;

    @Digits(fraction = 4, integer = 15)
    private BigDecimal amt;

    @Valid
    private List<PoSrvDto.Create> poSrv;
  }

  @Data
  public static class Response {
    private String poNo;
    private String itemSeq;
    private String prNo;
    private String prItemSeq;
    private String itemNm;
    private String itemCd;
    private String clsCd;
    private String sizeNm;
    private String modelNm;
    private String mkCompNm;
    private String dlvLoc;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private Date dlvReqYmd;
    private String unitCd;
    private BigDecimal qty;
    private BigDecimal unitPrc;
    private BigDecimal amt;
  }

}
