package kr.co.inogard.enio.api.domain.rfq;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import kr.co.inogard.enio.api.commons.validator.MaxByteLength;
import lombok.Getter;
import lombok.Setter;

public class RfqCusDto {

  @Getter
  @Setter
  public static class Create {
    @NotBlank
    @Size(min = 20)
    @MaxByteLength(20)
    private String rfqNo;

    @NotBlank
    @Size(min = 10)
    @MaxByteLength(10)
    private String cusCd;

    @MaxByteLength(2)
    private String quotRev;

    @MaxByteLength(1)
    private String rsltYn;

    @DateTimeFormat(pattern = "yyyyMMdd")
    private Date poYmd;
    
    @MaxByteLength(40)
	private String cusChrgrNm;
	
    @MaxByteLength(100)
    private String cusChrgrEmail;
    
    @MaxByteLength(20)
	private String cusChrgrTel;
    
    @MaxByteLength(20)
	private String cusChrgrSms;
    
    @MaxByteLength(20)
	private String cusChrgrFax;
    
    @DateTimeFormat(pattern = "yyyyMMddHHmmss")
	private Date quotSbmtDt;
    
    @MaxByteLength(20)
	private String techScore;
    
    @MaxByteLength(20)
	private String amtScore;
	
    @Digits(fraction = 4, integer = 19)
    private BigDecimal guaranteeAmt;
    
    @MaxByteLength(1)
	private String spotDscrChkYn;
	
    @MaxByteLength(1)
    private String rfqdocChkYn;
  }
}
