package kr.co.inogard.enio.api.web;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.inogard.enio.api.commons.constant.RsltCd;
import kr.co.inogard.enio.api.domain.dept.DeptDto;
import kr.co.inogard.enio.api.service.dept.DeptService;


@RestController
@RequestMapping(value = "/api/v1/depts", produces = "application/enio-json;charset=UTF-8")
public class DeptRestController {

  @Autowired
  private DeptService deptService;

  @Autowired
  ObjectMapper objectMapper;

  @RequestMapping(method = POST)
  @ResponseStatus(HttpStatus.CREATED)
  public DeptDto.Response create(@RequestBody DeptDto.Create create) throws IOException {
    List<DeptDto.ResponseEntry> resDeptList = deptService.create(create);

    DeptDto.Response res = new DeptDto.Response();
    res.setRsltCd(RsltCd.SUC0000.name());
    res.setRsltMsg(RsltCd.SUC0000.getCodeNm());
    res.setDatas(resDeptList);

    return res;
  }
}
