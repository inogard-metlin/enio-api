package kr.co.inogard.enio.api.mappers.quot;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.inogard.enio.api.domain.quot.Quot;

@Mapper
public interface QuotMapper {
	List<Quot> findAllByRfqNo(String rfqNo);

}
