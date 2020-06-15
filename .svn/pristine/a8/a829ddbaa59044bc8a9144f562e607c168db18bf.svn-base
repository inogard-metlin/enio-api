package kr.co.inogard.enio.api.web;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.inogard.enio.api.commons.constant.RsltCd;
import kr.co.inogard.enio.api.domain.user.User;
import kr.co.inogard.enio.api.domain.user.UserDto;
import kr.co.inogard.enio.api.service.user.UserService;

@RestController
@RequestMapping(value = "/api/v1/users", produces = "application/enio-json;charset=UTF-8")
public class UserRestController {

  @Autowired
  private UserService userService;

  @Autowired
  ObjectMapper objectMapper;

  @RequestMapping(method = POST)
  @ResponseStatus(HttpStatus.CREATED)
  public UserDto.Response create(@RequestBody UserDto.Create create) throws IOException {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String orgCd = authentication.getName();

    UserDto.Response res = new UserDto.Response();
    res.setRsltCd(RsltCd.SUC0000.name());
    res.setRsltMsg(RsltCd.SUC0000.getCodeNm());
    res.setDatas(new ArrayList<UserDto.ResponseEntry>());

    for (Iterator<UserDto.CreateEntry> iter = create.getDatas().iterator(); iter.hasNext();) {
      UserDto.CreateEntry cr = iter.next();
      User user = new User();
      user.setOrgCd(orgCd);
      user.setUserNm(cr.getUserNm());
      user.setUserTel(cr.getUserTel());
      user.setUserSms(cr.getUserSms());
      user.setUserEmail(cr.getUserEmail());
      user.setDeptCd(cr.getDeptCd());
      userService.createUser(user);

      UserDto.ResponseEntry resEntry = new UserDto.ResponseEntry();
      resEntry.setErpUserCd(cr.getUserCd());
      resEntry.setE4uUserCd(user.getUserCd());
      res.getDatas().add(resEntry);
    }
    return res;
  }
}
