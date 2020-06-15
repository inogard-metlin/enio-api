package kr.co.inogard.enio.api.commons.exception;

import lombok.extern.slf4j.Slf4j;

@SuppressWarnings("serial")
@Slf4j
public class FtpException extends RuntimeException {

  public FtpException(String message) {
    super(message);
    log.error(message, this);
  }

  public FtpException(Exception e) {
    super(e);
    log.error(e.getMessage(), e);
  }

}