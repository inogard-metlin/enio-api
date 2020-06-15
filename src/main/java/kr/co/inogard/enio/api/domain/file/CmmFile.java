package kr.co.inogard.enio.api.domain.file;

import java.math.BigDecimal;
import java.util.Date;

import lombok.Data;

@Data
public class CmmFile {
	private String fileNo;
	private String cliFilenm;
	private String svrFilenm;
	private String fileDir;
	private BigDecimal fileSz;
	private Date regDt;
	private String regUsrcd;
	private String fileRmrk;
	private String useYn;
	private String fileType;	
}
