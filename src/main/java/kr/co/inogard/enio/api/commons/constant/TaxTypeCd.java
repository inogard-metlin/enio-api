package kr.co.inogard.enio.api.commons.constant;

import lombok.Getter;

@Getter
public enum TaxTypeCd {
	A1000("매입VAT 10%"), A6000("매입VAT 0%(영세율)"), AI000("Invoice(비과세)");
	
	private String codeNm;
	
	private TaxTypeCd(String codeNm) {
		this.codeNm = codeNm;
	}
}
