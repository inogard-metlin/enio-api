package kr.co.inogard.enio.api.domain.dept;

import java.util.List;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import kr.co.inogard.enio.api.commons.validator.MaxByteLength;
import lombok.Data;

public class DeptDto {

	@Data
	public static class Create {
		private List<CreateEntry> datas;
	}

	@Data
	public static class CreateEntry {
		@NotBlank
		@Size(min = 10)
		@MaxByteLength(10)
		private String deptCd;

		@NotBlank
		@MaxByteLength(120)
		private String deptNm;
		
		@MaxByteLength(19)
		private String deptTel;
	}

	@Data
	public static class Response {
		private String rsltCd;
		private String rsltMsg;

		private List<ResponseEntry> datas;
	}

	@Data
	public static class ResponseEntry {
		private String e4uDeptCd, erpDeptCd;
	}

}