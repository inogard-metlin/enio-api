package kr.co.inogard.enio.api.domain.po;

import java.util.Date;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.inogard.enio.api.commons.validator.MaxByteLength;
import lombok.Data;

@Data
public class PoFileDto {

	@Data
	public static class Create {
		@NotBlank
		@Size(min = 20)
		@MaxByteLength(20)
		private String poNo;

		@NotBlank
		@Size(min = 5)
		@MaxByteLength(5)
		private String fileSeq;

		@Size(min = 20)
		@MaxByteLength(20)
		private String fileNo;
	}

	@Data
	public static class Response {
		private String poNo;
		private String fileSeq;
		private String fileNo;
		private String cliFileNm;
		private String svrFileNm;
		private String svrFilePath;
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		private Date regDt;
		private String erpPrNo;
	}
}
