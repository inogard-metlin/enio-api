package kr.co.inogard.enio.api.mappers.pr;

import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import kr.co.inogard.enio.api.domain.pr.Pr;

@Mapper
public interface PrMapper {

  void add(Pr pr);

  Pr findByPrNo(String prNo);

  Map<String, Object> findCancelResultAfterCancel(Map<String, Object> info);

}
