package kr.co.inogard.enio.api.domain.pr;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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

public class PrDto {

	@Data
	public static class Create {
		@NotBlank
		@MaxByteLength(30)
		private String prNo;

		@NotBlank
		@MaxByteLength(150)
		private String ttl;

		@NotBlank
		@Size(min = 1)
		@MaxByteLength(1)
		private String prTypecd;

		@NotBlank
		@Size(min = 5)
		@MaxByteLength(5)
		private String taxTypecd;

		@NotBlank
		@Size(min = 1)
		@MaxByteLength(1)
		private String ioFlag;

		@DateTimeFormat(pattern = "yyyyMMdd")
		private Date dlvFrYmd;

		@DateTimeFormat(pattern = "yyyyMMdd")
		private Date dlvToYmd;

		@NotBlank
		@MaxByteLength(120)
		private String dlvLoc;

		@NotNull
		@DateTimeFormat(pattern = "yyyyMMdd")
		private Date dlvReqYmd;

		@NotNull
		@Size(min = 1)
		@MaxByteLength(1)
		private String spotDscrYn;

		@DateTimeFormat(pattern = "yyyyMMddHHmmss")
		private Date spotDscrDt;

		@MaxByteLength(60)
		private String spotDscrUsrnm;

		@MaxByteLength(19)
		private String spotDscrUsrtel;

		@MaxByteLength(120)
		private String spotDscrLoc;

		@NotBlank
		@MaxByteLength(60)
		private String reqUsrNm;

		@MaxByteLength(40)
		private String reqUsrTel;

		@NotBlank
		@Size(min = 10)
		@MaxByteLength(10)
		private String gmChrgrCd;

		@NotBlank
		@Size(min = 10)
		@MaxByteLength(10)
		private String prChrgrCd;

		@NotBlank
		@Size(min = 10)
		@MaxByteLength(10)
		private String grChrgrCd;

		@NotBlank
		@Size(min = 10)
		@MaxByteLength(10)
		private String chkChrgrCd;

		@MaxByteLength(4000)
		private String prRmrk;

		@MaxByteLength(1)
		private String prebidYn;

		@MaxByteLength(1)
		private String bidSubTypecd;

		@MaxByteLength(1)
		private String bidTypecd;

		@MaxByteLength(5)
		private String selectedBidderStd;

		@Digits(fraction = 2, integer = 14)
		private BigDecimal bidderRate;

		@DateTimeFormat(pattern = "yyyyMMddHHmmss")
		private Date bidExpireDt;

		@MaxByteLength(1)
		private String rfqdocYn;

		@DateTimeFormat(pattern = "yyyyMMddHHmmss")
		private Date rfqdocExpireDt;

		@MaxByteLength(3000)
		private String rfqdocRmrk;

		@MaxByteLength(1)
		private String techdocYn;

		@DateTimeFormat(pattern = "yyyyMMddHHmmss")
		private Date techdocExpireDt;

		@MaxByteLength(3000)
		private String techdocRmrk;

		@MaxByteLength(4)
		private String constrPeriodInptKind;

		@MaxByteLength(300)
		private String constrPeriodRmrk;

		@MaxByteLength(7)
		private String subOrgCd;

		@NotNull
		private List<PrItemDto.Create> itemList;
	}

	@Data
	public static class Response {
		@JsonView(Views.ApiView.class)
		private String prNo;

		@JsonView(Views.ApiView.class)
		private String erpPrNo;

		@JsonView(Views.ApiView.class)
		private String rsltCd;

		@JsonView(Views.ApiView.class)
		private String rsltMsg;

		@JsonView(Views.PublicView.class)
		private String ttl;

		@JsonView(Views.PublicView.class)
		private String prTypecd;

		@JsonView(Views.PublicView.class)
		private String taxTypecd;

		@JsonView(Views.PublicView.class)
		private String ioFlag;

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
		private String spotDscrYn;

		@JsonView(Views.PublicView.class)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Asia/Seoul")
		private Date spotDscrDt;

		@JsonView(Views.PublicView.class)
		private String spotDscrUsrnm;

		@JsonView(Views.PublicView.class)
		private String spotDscrUsrtel;

		@JsonView(Views.PublicView.class)
		private String spotDscrLoc;

		@JsonView(Views.PublicView.class)
		private String reqUsrNm;

		@JsonView(Views.PublicView.class)
		private String reqUsrTel;

		@JsonView(Views.PublicView.class)
		private String gmChrgrCd;

		@JsonView(Views.PublicView.class)
		private String prChrgrCd;

		@JsonView(Views.PublicView.class)
		private String grChrgrCd;

		@JsonView(Views.PublicView.class)
		private String chkChrgrCd;

		@JsonView(Views.PublicView.class)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		private Date regDt;

		@JsonView(Views.PublicView.class)
		private String prRmrk;

		@JsonView(Views.PublicView.class)
		private String prebidYn;

		@JsonView(Views.PublicView.class)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		private Date e4uIfDt;

		@JsonView(Views.PublicView.class)
		private String erpRfqNo;

		@JsonView(Views.PublicView.class)
		private String bidSubTypecd;

		@JsonView(Views.PublicView.class)
		private String bidTypecd;

		@JsonView(Views.PublicView.class)
		private String e4uIfSt;

		@JsonView(Views.PublicView.class)
		private String cnclFlag;

		@JsonView(Views.PublicView.class)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		private Date cnclReqDt;

		@JsonView(Views.PublicView.class)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		private Date cnclRsltDt;

		@JsonView(Views.PublicView.class)
		private String cnclRsltMsg;

		@JsonView(Views.PublicView.class)
		private String subOrgCd;
	}

	@Data
	public static class ReqCancelCreate {
		private String prNo;
		private Date cnclReqDt;
	}

	@Data
	public static class ReqCancelResponse {
		private String rsltCd;
		private String rsltMsg;
		private String prNo;
		private String cnclFlag;
		private Date cnclReqDt;
		private Date cnclRsltDt;
		private String cnclRsltMsg;
	}

	@Data
	public static class Search {
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
