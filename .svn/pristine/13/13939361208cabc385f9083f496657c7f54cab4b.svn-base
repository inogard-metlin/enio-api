package kr.co.inogard.enio.api.domain.pr;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import kr.co.inogard.enio.api.commons.validator.MaxByteLength;
import lombok.Data;

public class PrSrvDto {
	
	@Data
	public static class Create {
		@NotBlank
		@MaxByteLength(30)
		private String prNo;
		
		@NotBlank
		@Size(min = 5)
		@MaxByteLength(5)
		private String itemSeq;
		
		@NotBlank
		@MaxByteLength(150)
		private String srvNm;
		
		@NotBlank
		@Size(min = 5)
		@MaxByteLength(5)
		private String srvCd;
		
		@NotBlank
		@MaxByteLength(5)
		private String unitCd;
		
		@NotNull
		@Digits(fraction = 2, integer = 12)
		private BigDecimal qty;
	}
}
