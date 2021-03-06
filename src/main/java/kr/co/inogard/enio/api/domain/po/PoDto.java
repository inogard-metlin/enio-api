package kr.co.inogard.enio.api.domain.po;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import kr.co.inogard.enio.api.commons.validator.MaxByteLength;
import lombok.Data;

public class PoDto {

	@Data
	public static class Create {
		@NotBlank
		@Size(min = 20)
		@MaxByteLength(20)
		private String poNo;

		@MaxByteLength(150)
		private String ttl;

		@DateTimeFormat(pattern = "yyyyMMdd")
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyyMMdd", timezone = "Asia/Seoul")
		private Date poYmd;

		@Digits(fraction = 4, integer = 15)
		private BigDecimal poAmt;

		@Digits(fraction = 4, integer = 15)
		private BigDecimal vatAmt;

		@Size(min = 1)
		@MaxByteLength(1)
		private String prTypecd;

		@Size(min = 5)
		@MaxByteLength(5)
		private String taxTypecd;

		@Size(min = 1)
		@MaxByteLength(1)
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

		@Size(min = 10)
		@MaxByteLength(10)
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

		@Size(min = 3)
		@MaxByteLength(3)
		private String crcyCd;

		@Digits(fraction = 4, integer = 19)
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
		
		@NotNull
		@Valid
		private List<PoItemDto.Create> poItem;

		// @NotNull
		// @Valid
		// private List<PoFileDto.Create> poFile;
	}

	@Data
	public static class Response {

		@JsonView(Views.ApiView.class)
		private String poNo;

		@JsonView(Views.ApiView.class)
		private String rsltCd;

		@JsonView(Views.ApiView.class)
		private String rsltMsg;

		@JsonView(Views.ApiView.class)
		private String erpPoNo;

		@JsonView(Views.PublicView.class)
		private String ttl;

		@JsonView(Views.PublicView.class)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date poYmd;

		@JsonView(Views.PublicView.class)
		private BigDecimal poAmt;

		@JsonView(Views.PublicView.class)
		private BigDecimal vatAmt;

		@JsonView(Views.PublicView.class)
		private String prTypecd;

		@JsonView(Views.PublicView.class)
		private String taxTypecd;

		@JsonView(Views.PublicView.class)
		private String ioFlag;

		@JsonView(Views.PublicView.class)
		private String chrgUsrcd;

		@JsonView(Views.PublicView.class)
		private String chrgDeptcd;

		@JsonView(Views.PublicView.class)
		private String orgBsnNo;

		@JsonView(Views.PublicView.class)
		private String orgNm;

		@JsonView(Views.PublicView.class)
		private String orgCeoNm;

		@JsonView(Views.PublicView.class)
		private String orgMainTel;

		@JsonView(Views.PublicView.class)
		private String orgMainFax;

		@JsonView(Views.PublicView.class)
		private String orgMainEmail;

		@JsonView(Views.PublicView.class)
		private String orgUptaeNm;

		@JsonView(Views.PublicView.class)
		private String orgUpjongNm;

		@JsonView(Views.PublicView.class)
		private String orgZipCd;

		@JsonView(Views.PublicView.class)
		private String orgAddr;

		@JsonView(Views.PublicView.class)
		private String cusCd;

		@JsonView(Views.PublicView.class)
		private String cusBsnNo;

		@JsonView(Views.PublicView.class)
		private String cusNm;

		@JsonView(Views.PublicView.class)
		private String cusCeoNm;

		@JsonView(Views.PublicView.class)
		private String cusMainTel;

		@JsonView(Views.PublicView.class)
		private String cusMainFax;

		@JsonView(Views.PublicView.class)
		private String cusMainEmail;

		@JsonView(Views.PublicView.class)
		private String cusUptaeNm;

		@JsonView(Views.PublicView.class)
		private String cusUpjongNm;

		@JsonView(Views.PublicView.class)
		private String cusZipCd;

		@JsonView(Views.PublicView.class)
		private String cusAddr;

		@JsonView(Views.PublicView.class)
		private String crcyCd;

		@JsonView(Views.PublicView.class)
		private BigDecimal crcyRate;

		@JsonView(Views.PublicView.class)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date dlvFrYmd;

		@JsonView(Views.PublicView.class)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date dlvToYmd;

		@JsonView(Views.PublicView.class)
		private String dlvLoc;

		@JsonView(Views.PublicView.class)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
		private Date dlvReqYmd;

		@JsonView(Views.PublicView.class)
		private String prepayInsrYn;

		@JsonView(Views.PublicView.class)
		private BigDecimal prepayAmt;

		@JsonView(Views.PublicView.class)
		private String prfmInsrYn;

		@JsonView(Views.PublicView.class)
		private BigDecimal prfmInsrRate;

		@JsonView(Views.PublicView.class)
		private BigDecimal prfmInsrAmt;

		@JsonView(Views.PublicView.class)
		private String flawInsrYn;

		@JsonView(Views.PublicView.class)
		private BigDecimal flawInsrRate;

		@JsonView(Views.PublicView.class)
		private String flawInsrYear;

		@JsonView(Views.PublicView.class)
		private String delayRateYn;

		@JsonView(Views.PublicView.class)
		private BigDecimal delayRate;

		@JsonView(Views.PublicView.class)
		private BigDecimal delayMaxRate;

		@JsonView(Views.PublicView.class)
		private String dlvCondRmrk;

		@JsonView(Views.PublicView.class)
		private String payCondRmrk;

		@JsonView(Views.PublicView.class)
		private String poDocKind;

		@JsonView(Views.PublicView.class)
		private String offlineYn;

		@JsonView(Views.PublicView.class)
		private String poRmrk;

		@JsonView(Views.PublicView.class)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		private Date regDt;

		@JsonView(Views.PublicView.class)
		private String poSt;

		@JsonView(Views.PublicView.class)
		private String rfqNo;
	}

	@Data
	public static class Search {
		private String value;

		@MaxByteLength(3)
		private String e4uIfSt;
	}

	public static class Views {
		public static interface ApiView {
		}

		public static interface PublicView extends ApiView, DataTablesOutput.View {
		}
	}
}
