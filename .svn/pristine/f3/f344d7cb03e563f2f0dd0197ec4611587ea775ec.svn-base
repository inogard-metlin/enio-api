package kr.co.inogard.enio.api.service.cus;

import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import kr.co.inogard.enio.api.domain.cus.Customer;
import kr.co.inogard.enio.api.mappers.cus.CusMapper;

@Service
@Transactional
public class CusService {

  @Autowired
  private CusMapper cusMapper;

  public List<Customer> getAllByRfqNo(String rfqNo) {
    return cusMapper.findAllByRfqNo(rfqNo);
  }

}
