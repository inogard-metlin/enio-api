package kr.co.inogard.enio.api.mappers.rfq;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.inogard.enio.api.domain.rfq.RfqDegree;

@Mapper
public interface RfqDegreeMapper {
	void add(RfqDegree rfqDegree);

	List<RfqDegree> findAllByRfqNo(String rfqNo);
}
