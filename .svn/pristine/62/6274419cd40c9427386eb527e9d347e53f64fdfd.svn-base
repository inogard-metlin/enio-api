package kr.co.inogard.enio.api.commons.exception;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("serial")
@Slf4j
public class EnioRunTimeException extends RuntimeException {

  public EnioRunTimeException(String message) {
    super(message);
    log.error(message, this);
  }

  public EnioRunTimeException(Exception e) {
    super(e);
    log.error(e.getMessage(), e);
  }
}
