package kr.co.inogard.enio.api.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.inogard.enio.api.domain.user.User;
import kr.co.inogard.enio.api.mappers.user.UserMapper;

@Service
@Transactional
public class UserService {

  @Autowired
  private UserMapper userMapper;


  public void createUser(User user) {
    userMapper.add(user);
  }

  public User getByUserCd(String userCd) {
    return userMapper.findByUserCd(userCd);
  }
}
