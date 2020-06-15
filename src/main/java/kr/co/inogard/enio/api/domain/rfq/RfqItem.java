package kr.co.inogard.enio.api.domain.rfq;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RfqItem {
	private String rfqNo ; 
	private String itemSeq ; 
	private String prNo ;
	private String prItemSeq ;
	private String itemNm;
	private String itemCd;
	private String clsCd;
	private String sizeNm;
	private String modelNm;
	private String mkCompNm;
	private String dlvLoc;
	private Date dlvReqYmd;		
	private String unitCd;
	private BigDecimal planUnitPrc;	
	private BigDecimal qty ;
	
	private List<RfqSrv> rfqSrvList;

}
