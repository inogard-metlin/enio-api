package kr.co.inogard.enio.api.domain.quot;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import kr.co.inogard.enio.api.commons.validator.MaxByteLength;
import lombok.Data;

@Data
public class QuotItemDto {
	
	@NotBlank
	@Size(min = 20)
	@MaxByteLength(20)
	private String rfqNo;
	
	@NotBlank
	@Size(min = 2)
	@MaxByteLength(2)
	private String quotRev;
	
	@NotBlank
	@Size(min = 10)
	@MaxByteLength(10)
	private String cusCd;
	
	@NotBlank
	@Size(min = 5)
	@MaxByteLength(5)
	private String itemSeq;
	
	@MaxByteLength(5)
	private String rfqItemSeq;
	
	@Digits(fraction = 2, integer = 12)
	private BigDecimal qty;
	
	@Digits(fraction = 4, integer = 15)
	private BigDecimal unitPrc;
	
	@Digits(fraction = 4, integer = 15)
	private BigDecimal amt;
	
	@Valid
	private List<QuotSrvDto> quotSrvList;
	
}

