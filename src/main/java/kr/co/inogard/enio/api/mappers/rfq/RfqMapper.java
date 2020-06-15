package kr.co.inogard.enio.api.mappers.rfq;

import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import kr.co.inogard.enio.api.domain.rfq.Rfq;

@Mapper
public interface RfqMapper {
  void add(Rfq Rfq);

  Rfq findByRfqNo(String rfqNo);

  String findNewRfqNoAfterCreateFromPr(Map<String, Object> prInfo);
}
