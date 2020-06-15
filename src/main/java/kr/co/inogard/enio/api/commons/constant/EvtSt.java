package kr.co.inogard.enio.api.commons.constant;

import lombok.Getter;

@Getter
public enum EvtSt {
  R("대기"), W("실행"), C("처리완료");

  private String codeNm;

  private EvtSt(String codeNm) {
    this.codeNm = codeNm;
  }
}
