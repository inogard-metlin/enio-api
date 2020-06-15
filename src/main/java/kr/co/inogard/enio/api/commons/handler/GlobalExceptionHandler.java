package kr.co.inogard.enio.api.commons.handler;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import kr.co.inogard.enio.api.commons.constant.RsltCd;
import kr.co.inogard.enio.api.commons.domain.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@RestController
@Slf4j
public class GlobalExceptionHandler {

  @Autowired
  private ModelMapper modelMapper;

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorResponse methodArgumentNotValidException(MethodArgumentNotValidException e) {
    log.info("=============== Method Argument Not Valid Exception START ===============");
    Type listType = new TypeToken<List<ErrorResponse.FieldError>>() {}.getType();
    List<ErrorResponse.FieldError> errors =
        modelMapper.map(e.getBindingResult().getFieldErrors(), listType);

    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setRsltCd(RsltCd.ERR0000.name());
    errorResponse.setRsltMsg("Request value validation error.");
    errorResponse.setErrors(errors);
    log.info("errorResponse : {}", errorResponse);
    log.info("=============== Method Argument Not Valid Exception END  ===============");
    return errorResponse;
  }

  @ExceptionHandler(RuntimeException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse runTimeException(Exception e) {
    log.info("==================== RunTime Exception START ====================");
    log.error(e.getMessage(), e);
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setRsltCd(RsltCd.ERR1000.name());
    errorResponse.setRsltMsg(e.getMessage());
    log.info("errorResponse : {}", errorResponse);
    log.info("==================== RunTime Exception END  ====================");
    return errorResponse;
  }

  @ExceptionHandler(SQLException.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse sqlException(Exception e) {
    log.info("==================== SQL Exception START ====================");
    log.error(e.getMessage(), e);
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setRsltCd(RsltCd.ERR2000.name());
    errorResponse.setRsltMsg(e.getMessage());
    log.info("errorResponse : {}", errorResponse);
    log.info("==================== SQL Exception END  ====================");
    return errorResponse;
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorResponse exception(Exception e) {
    log.info("==================== Exception START ====================");
    log.error(e.getMessage(), e);
    ErrorResponse errorResponse = new ErrorResponse();
    errorResponse.setRsltCd(RsltCd.ERR9999.name());
    errorResponse.setRsltMsg(e.getMessage());
    log.info("errorResponse : {}", errorResponse);
    log.info("==================== Exception END  ====================");
    return errorResponse;
  }
}
