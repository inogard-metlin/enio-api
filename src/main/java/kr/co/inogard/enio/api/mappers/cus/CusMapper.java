package kr.co.inogard.enio.api.mappers.cus;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.inogard.enio.api.domain.cus.Customer;

@Mapper
public interface CusMapper {
	List<Customer> findAllByRfqNo(String rfqNo);
}
