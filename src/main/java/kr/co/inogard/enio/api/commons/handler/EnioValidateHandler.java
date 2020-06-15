package kr.co.inogard.enio.api.commons.handler;

import java.lang.reflect.Type;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Validator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.inogard.enio.api.commons.constant.RsltCd;
import kr.co.inogard.enio.api.commons.domain.ErrorResponse;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EnioValidateHandler {

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  Validator validator;

  public void validate(Object obj) {
    DataBinder binder = new DataBinder(obj);
    binder.setValidator(validator);
    binder.validate();
    BindingResult results = binder.getBindingResult();
    if (results.hasErrors()) {
      Type listType = new TypeToken<List<ErrorResponse.FieldError>>() {}.getType();
      List<ErrorResponse.FieldError> errors = modelMapper.map(results.getFieldErrors(), listType);

      ErrorResponse errorResponse = new ErrorResponse();
      errorResponse.setRsltCd(RsltCd.ERR0000.name());
      errorResponse.setRsltMsg("Request value validation error.");
      errorResponse.setErrors(errors);
      String jsonString = null;
      try {
        jsonString = objectMapper.writeValueAsString(errorResponse);
      } catch (JsonProcessingException e) {
        log.error(e.getMessage(), e);
        throw new RuntimeException(e);
      }
      throw new RuntimeException(jsonString);
    }
  }
}
