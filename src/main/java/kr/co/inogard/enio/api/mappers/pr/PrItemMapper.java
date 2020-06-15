package kr.co.inogard.enio.api.mappers.pr;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.inogard.enio.api.domain.pr.PrFile;
import kr.co.inogard.enio.api.domain.pr.PrItem;

@Mapper
public interface PrItemMapper {
	void add(PrItem prItem);
	List<PrItem> findAllByPrNo(String prNo);	
}
