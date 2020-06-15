package kr.co.inogard.enio.api.mappers.dept;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.co.inogard.enio.api.domain.dept.Dept;

@Mapper
public interface DeptMapper {
	void add(Dept dept);	
	
	List<Dept> findAll();
}
