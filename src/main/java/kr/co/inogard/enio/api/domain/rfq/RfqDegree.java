package kr.co.inogard.enio.api.domain.rfq;

import java.util.Date;

import lombok.Data;

@Data
public class RfqDegree {
	private String rfqNo ; 
	private String quotRev ; 
	private Date bidStartDt ;
	private Date bidExpireDt ;
	private Date bidOpenDt ;
	private String rebidRmrk ;

}
