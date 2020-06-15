package kr.co.inogard.enio.api.mappers.pr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.inogard.enio.api.domain.pr.PrMap;

@Mapper
public interface PrMapMapper {
	
	void add(PrMap prMap);
	List<PrMap> findAll(Map srchMap);	
	PrMap findByRfqNo(String rfqNo);
	
	
}
