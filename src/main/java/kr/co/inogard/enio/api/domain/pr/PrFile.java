package kr.co.inogard.enio.api.domain.pr;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrFile {
	private String prNo;
	private String fileSeq;
	private String fileNo;
	private String cliFileNm;
	private String svrFileNm;
	private String svrFilePath;
	private Date regDt;
	private String erpPrNo;
}