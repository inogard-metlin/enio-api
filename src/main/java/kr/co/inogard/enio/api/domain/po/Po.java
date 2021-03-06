package kr.co.inogard.enio.api.domain.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Po {
	private String poNo;
	private String ttl;
	private Date poYmd;
	private BigDecimal poAmt;
	private BigDecimal vatAmt;
	private String prTypecd;
	private String taxTypecd;
	private String ioFlag;
	private String chrgUsrcd;
	private String chrgDeptcd;
	private String orgBsnNo;
	private String orgNm;
	private String orgCeoNm;
	private String orgMainTel;
	private String orgMainFax;
	private String orgMainEmail;
	private String orgUptaeNm;
	private String orgUpjongNm;
	private String orgZipCd;
	private String orgAddr;
	private String cusCd;
	private String cusBsnNo;
	private String cusNm;
	private String cusCeoNm;
	private String cusMainTel;
	private String cusMainFax;
	private String cusMainEmail;
	private String cusUptaeNm;
	private String cusUpjongNm;
	private String cusZipCd;
	private String cusAddr;
	private String crcyCd;
	private BigDecimal crcyRate;
	private Date dlvFrYmd;
	private Date dlvToYmd;
	private String dlvLoc;
	private Date dlvReqYmd;
	private String prepayInsrYn;
	private BigDecimal prepayAmt;
	private String prfmInsrYn;
	private BigDecimal prfmInsrRate;
	private BigDecimal prfmInsrAmt;
	private String flawInsrYn;
	private BigDecimal flawInsrRate;
	private String flawInsrYear;
	private String delayRateYn;
	private BigDecimal delayRate;
	private BigDecimal delayMaxRate;
	private String dlvCondRmrk;
	private String payCondRmrk;
	private String poDocKind;
	private String offlineYn;
	private String poRmrk;
	private Date regDt;
	private String poSt;
	private String rfqNo;
	private String subOrgCd;
	
	List<PoItem> itemList;
	// List<PoFile> fileList;

}
