package kr.co.inogard.enio.api.domain.pr;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrItem {
	private String prNo;
	private String itemSeq;
	private String itemNm;
	private String itemCd;
	private String clsCd;
	private String sizeNm;
	private String modelNm;
	private String dlvLoc;
	private Date dlvReqYmd;
	private String mkCompNm;
	private String unitCd;
	private BigDecimal qty;
	private BigDecimal planUnitPrc;
	private String rfqNo;
	private String poNo;
	private String itemSt;
	private String erpPrNo;

	/*
	 * 2018.06.28 김원기 추가 덕성여대 요구
	 */
	private String constrLoc;
	private Date constrFrDt;
	private Date constrToDt;
	private String constrPeriodInptKind;
	private String constrPeriodRmrk;

	List<PrSrv> srvList;
}
