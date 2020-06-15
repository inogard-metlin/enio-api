package kr.co.inogard.enio.api.domain.quot;

import java.util.Date;

import lombok.Data;

@Data
public class QuotFile {

	private String rfqNo;
	private String quotRev;
	private String cusCd;
	private String fileSeq;
	private String fileNo;
	private String cliFileNm;
	private String svrFileNm;
	private String svrFilePath;
	private Date regDt;

}
