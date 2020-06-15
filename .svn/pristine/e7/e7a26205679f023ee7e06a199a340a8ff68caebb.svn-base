package kr.co.inogard.enio.api.domain.quot;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import kr.co.inogard.enio.api.commons.validator.MaxByteLength;
import lombok.Data;

@Data
public class QuotFileDto {
	
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
	private String fileSeq;
	
	@MaxByteLength(20)
	private String fileNo;
	
}

