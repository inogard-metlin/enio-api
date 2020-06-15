package kr.co.inogard.enio.api.mappers.pr;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import kr.co.inogard.enio.api.domain.pr.PrSrv;

@Mapper
public interface PrSrvMapper {
	void add(PrSrv prSrv);
	List<PrSrv> findAll(Map srchMap);	
}
