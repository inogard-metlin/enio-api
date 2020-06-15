package kr.co.inogard.enio.api.mappers.file;

import org.apache.ibatis.annotations.Mapper;

import kr.co.inogard.enio.api.domain.file.CmmFile;

@Mapper
public interface FileMapper {

	void add(CmmFile cmmFile);
}
