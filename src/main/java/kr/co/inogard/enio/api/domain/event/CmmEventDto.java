package kr.co.inogard.enio.api.domain.event;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import kr.co.inogard.enio.api.commons.validator.MaxByteLength;
import lombok.Data;

public class CmmEventDto {

	@Data
	public static class Response {
		private String evtNo;
		private String evtIoType;
		private String evtCmdParam;
		private String evtSt;

		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		private Date regDt;
		private String reqCntn;
		private String rsltCd;
		private String rsltMsg;
		private String rcvCntn;

		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		private Date rcvDt;
		private String agtCd;
		private String scheKind;
	}

	@Data
	public static class Search {
		private String value;

		@MaxByteLength(1)
		private String evtSt;

		@MaxByteLength(3)
		private String evtIoType;

		@MaxByteLength(5)
		private String agtCd;
	}
}
