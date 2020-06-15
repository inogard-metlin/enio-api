package kr.co.inogard.enio.api.mappers.quot;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.inogard.enio.api.domain.quot.Quot;
import kr.co.inogard.enio.api.domain.quot.QuotItem;

@Mapper
public interface QuotItemMapper {

	List<QuotItem> findAllByQuot(Quot quot);

}
