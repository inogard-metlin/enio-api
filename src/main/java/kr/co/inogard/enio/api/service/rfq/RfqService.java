package kr.co.inogard.enio.api.service.rfq;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.inogard.enio.api.domain.rfq.Rfq;
import kr.co.inogard.enio.api.domain.rfq.RfqCus;
import kr.co.inogard.enio.api.domain.rfq.RfqDegree;
import kr.co.inogard.enio.api.domain.rfq.RfqItem;
import kr.co.inogard.enio.api.domain.rfq.RfqSrv;
import kr.co.inogard.enio.api.mappers.rfq.RfqCusMapper;
import kr.co.inogard.enio.api.mappers.rfq.RfqDegreeMapper;
import kr.co.inogard.enio.api.mappers.rfq.RfqItemMapper;
import kr.co.inogard.enio.api.mappers.rfq.RfqMapper;
import kr.co.inogard.enio.api.mappers.rfq.RfqSrvMapper;

@Service
@Transactional
public class RfqService {

  @Autowired
  private RfqMapper rfqMapper;

  @Autowired
  private RfqItemMapper rfqItemMapper;

  @Autowired
  private RfqSrvMapper rfqSrvMapper;

  @Autowired
  private RfqCusMapper rfqCusMapper;

  @Autowired
  private RfqDegreeMapper rfqDegreeMapper;

  public Rfq getByRfqNo(String rfqNo) {
    return rfqMapper.findByRfqNo(rfqNo);
  }

  public Rfq createFromPr(Map<String, Object> prInfo) {
    String newRfqNo = rfqMapper.findNewRfqNoAfterCreateFromPr(prInfo);
    return rfqMapper.findByRfqNo(newRfqNo);
  }

  public List<RfqSrv> getAllbyRfqItem(RfqItem rfqItem) {
    return rfqSrvMapper.findAllbyRfqItem(rfqItem);
  }

  public List<RfqSrv> getAllRfqService(RfqItem rfqItem) {
    return rfqSrvMapper.findAllbyRfqItem(rfqItem);
  }

  public List<RfqCus> getAllRfqCus(String rfqNo) {
    return rfqCusMapper.findAllByRfqNo(rfqNo);
  }

  public List<RfqDegree> getAllRfqDegree(String rfqNo) {
    return rfqDegreeMapper.findAllByRfqNo(rfqNo);
  }

  public List<RfqItem> getAllRfqItems(String rfqNo) {
    return rfqItemMapper.findAllByRfqNo(rfqNo);
  }

}
