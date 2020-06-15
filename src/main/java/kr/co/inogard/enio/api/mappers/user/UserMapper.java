package kr.co.inogard.enio.api.mappers.user;

import org.apache.ibatis.annotations.Mapper;

import kr.co.inogard.enio.api.domain.user.User;

@Mapper
public interface UserMapper {
	void add(User user);

	User findByUserCd(String userCd);	
}
