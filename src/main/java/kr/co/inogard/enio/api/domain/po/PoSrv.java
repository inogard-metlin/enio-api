package kr.co.inogard.enio.api.domain.po;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class PoSrv {
	private String poNo;
	private String itemSeq;
	private String srvSeq;
	private String prNo;
	private String prItemSeq;
	private String prSrvSeq;
	private String srvNm;
	private String srvCd;
	private String unitCd;	
	private BigDecimal qty;
	private BigDecimal unitPrc;
	private BigDecimal amt;
}
