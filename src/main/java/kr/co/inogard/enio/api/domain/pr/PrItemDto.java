package kr.co.inogard.enio.api.domain.pr;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import kr.co.inogard.enio.api.commons.validator.MaxByteLength;
import lombok.Data;

public class PrItemDto {

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
		private String itemNm;

		@NotBlank
		@Size(min = 20)
		@MaxByteLength(20)
		private String itemCd;

		@NotBlank
		@MaxByteLength(10)
		private String clsCd;

		@NotBlank
		@MaxByteLength(120)
		private String sizeNm;

		@NotBlank
		@MaxByteLength(150)
		private String modelNm;

		@NotBlank
		@MaxByteLength(6)
		private String unitCd;

		@NotNull
		@Digits(fraction = 2, integer = 12)
		private BigDecimal qty;

		@NotNull
		@Digits(fraction = 2, integer = 17)
		private BigDecimal planUnitPrc;

		@MaxByteLength(120)
		private String mkCompNm;

		@NotBlank
		@MaxByteLength(120)
		private String dlvLoc;

		@NotNull
		@DateTimeFormat(pattern = "yyyyMMdd")
		private Date dlvReqYmd;

		@NotBlank
		@MaxByteLength(4)
		private String constrPeriodInptKind;

		@NotBlank
		@MaxByteLength(40)
		private String constrPeriodRmrk;

		@Valid
		private List<PrSrvDto.Create> srvList;
	}

}
