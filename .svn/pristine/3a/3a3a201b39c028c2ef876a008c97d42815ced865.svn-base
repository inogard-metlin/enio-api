package kr.co.inogard.enio.api.mappers.rfq;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.inogard.enio.api.domain.rfq.RfqItem;

@Mapper
public interface RfqItemMapper {
	void add(RfqItem rfqItem);

	List<RfqItem> findAllByRfqNo(String rfqNo);
}
