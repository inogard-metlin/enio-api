package kr.co.inogard.enio.api.domain.file;

import java.math.BigDecimal;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;


public class CmmFileDto {
	
	@Data
	public static class Create {
		private String fileNo;
		@NotBlank
		private String cliFilenm;
		private String svrFilenm;
		private String fileDir;
		private BigDecimal fileSz;
		private Date regDt;
		@NotBlank
		private String regUsrcd;
		private String fileRmrk;
		private String useYn;
		private String fileType;
	}
	
}
