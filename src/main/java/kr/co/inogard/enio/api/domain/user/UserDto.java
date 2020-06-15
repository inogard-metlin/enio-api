package kr.co.inogard.enio.api.domain.user;

import java.util.List;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import kr.co.inogard.enio.api.commons.validator.MaxByteLength;
import lombok.Data;

public class UserDto {

	@Data
	public static class Create {
	    private List<CreateEntry> datas;		
	}
	
	@Data
	public static class CreateEntry{
      @NotBlank
      @Size(min = 10)
      @MaxByteLength(10)
      private String userCd;
    
      @NotBlank
      @MaxByteLength(60)
      private String userNm;
      
      @NotBlank
      @MaxByteLength(19)
      private String userTel;
      
      @NotBlank
      @MaxByteLength(450)        
      private String userEmail;
      
      @NotBlank
      @Size(min = 10)
      @MaxByteLength(10)        
      private String deptCd;
      
      @NotBlank
      @Size(min = 10)
      @MaxByteLength(10)
      private String userSms;
      
      private String orgCd;
      private String cusCd;
	}
	
	@Data
	public static class Response {
	    private String rsltCd;
	    private String rsltMsg;

	    private List<ResponseEntry> datas;		
	}
	
	@Data
	public static class ResponseEntry{
		private String erpUserCd;
		private String e4uUserCd;
	}
}
