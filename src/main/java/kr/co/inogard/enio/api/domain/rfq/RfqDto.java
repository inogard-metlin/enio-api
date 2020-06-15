package kr.co.inogard.enio.api.domain.rfq;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import kr.co.inogard.enio.api.commons.validator.MaxByteLength;
import lombok.Data;

public class RfqDto {

	@Data
	public static class Create {
		@NotBlank
		@Size(min = 20)
		@MaxByteLength(20)
		private String rfqNo;

		@MaxByteLength(150)
		private String ttl;

		@Size(min = 2)
		@MaxByteLength(2)
		private String quotRev;

		@NotBlank
		@Size(min = 1)
		@MaxByteLength(1)
		private String prTypecd;

		@NotBlank
		@Size(min = 1)
		@MaxByteLength(1)
		private String ioFlag;

		@NotBlank
		@Size(min = 5)
		@MaxByteLength(5)
		private String taxTypecd;

		private Date dlvFrYmd;
		private Date dlvToYmd;
		private String dlvLoc;
		private Date dlvReqYmd;
		private String spotDscrYn;
		private Date spotDscrDt;
		private String spotDscrUsrnm;
		private String spotDscrUsrtel;
		private String spotDscrLoc;
		private String dlvCondRmrk;
		private String payCondRmrk;
		private BigDecimal bidPlanAmt;
		private String bidSubTypecd;
		private String bidTypecd;
		private String bidInsrYn;
		private String bidInsrRate;
		private String rfqRmrk;
		private String chrgUsrcd;
		private String chrgDeptcd;
		private String rfqSt;
		private Date regDt;

		private String selectedBidderStd;
		private BigDecimal bidderRate;
		private Date bidExpireDt;
		private String rfqdocYn;
		private Date rfqdocExpireDt;
		private String rfqdocRmrk;
		private String techdocYn;
		private Date techdocExpireDt;
		private String techdocRmrk;
		private String constrPeriodInptKind;
		private String constrPeriodRmrk;
		
		private String subOrgCd;

		@Valid
		private List<RfqCusDto.Create> rfqCusList;

		@Valid
		private List<RfqDegreeDto.Create> rfqDegreeList;

		@Valid
		private List<RfqItemDto.Create> rfqItemList;
	}

	@Data
	public static class Response {
		@JsonView(Views.ApiView.class)
		private String rfqNo;

		@JsonView(Views.ApiView.class)
		private String erpRfqNo;

		@JsonView(Views.ApiView.class)
		private String rsltCd;

		@JsonView(Views.ApiView.class)
		private String rsltMsg;

		@JsonView(Views.PublicView.class)
		private String ttl;

		@JsonView(Views.PublicView.class)
		private String quotRev;

		@JsonView(Views.PublicView.class)
		private String prTypecd;

		@JsonView(Views.PublicView.class)
		private String ioFlag;

		@JsonView(Views.PublicView.class)
		private String taxTypecd;

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
		private String dlvCondRmrk;

		@JsonView(Views.PublicView.class)
		private String payCondRmrk;

		@JsonView(Views.PublicView.class)
		private BigDecimal bidPlanAmt;

		@JsonView(Views.PublicView.class)
		private String bidSubTypecd;

		@JsonView(Views.PublicView.class)
		private String bidTypecd;

		@JsonView(Views.PublicView.class)
		private String bidInsrYn;

		@JsonView(Views.PublicView.class)
		private String bidInsrRate;

		@JsonView(Views.PublicView.class)
		private String rfqRmrk;

		@JsonView(Views.PublicView.class)
		private String chrgUsrcd;

		@JsonView(Views.PublicView.class)
		private String chrgDeptcd;

		@JsonView(Views.PublicView.class)
		private String rfqSt;

		@JsonView(Views.PublicView.class)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
		private Date regDt;
	}

	@Data
	public static class NotiCreate {
		String rfqNo;
		String rfqSt;
		String notiKind;
		String notiMsg;
	}

	@Data
	public static class NotiResponse {
		private String rsltCd;
		private String rsltMsg;
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
