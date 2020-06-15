package kr.co.inogard.enio.api.domain.cus;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;

import kr.co.inogard.enio.api.commons.validator.MaxByteLength;
import lombok.Data;

public class CustomerDto {
	
	@Data
	public static class Create {
	    private List<CreateEntry> datas;		
	}
	
	@Data
	public static class CreateEntry{
		@NotBlank @MaxByteLength(10)
		private String cusCd;
		
		@NotBlank @MaxByteLength(10)
		private String cusBsnNo;
		
		@NotBlank @MaxByteLength(120)
		private String cusNm;
		
		@MaxByteLength(60)
		private String ceoNm;
		
		@NotBlank @MaxByteLength(19)
		private String mainTel;
		
		@NotBlank @MaxByteLength(19)
		private String mainFax;
		
		@NotBlank @MaxByteLength(300)
		private String mainEmail;
		
		@NotBlank @MaxByteLength(120)
		private String uptaeNm;
		
		@NotBlank @MaxByteLength(120)
		private String upjongNm;
		
		@NotBlank @MaxByteLength(7)
		private String zipCd;
		
		@NotBlank @MaxByteLength(150)
		private String addr1;
		
		@NotBlank @MaxByteLength(150)
		private String addr2;
		
		private Date regDt;
		private String e4uCusCd;

	}
	
	@Data
	public static class Response {
	    private String rsltCd;
	    private String rsltMsg;

	    private List<ResponseEntry> datas;		
	}
	
	@Data
	public static class ResponseEntry{
		private String erpCusCd;
		private String e4uCusCd;
	}
	
	
}
