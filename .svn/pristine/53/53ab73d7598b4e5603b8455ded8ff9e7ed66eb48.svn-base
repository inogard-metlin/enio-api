package kr.co.inogard.enio.api.pr.item;

import java.text.ParseException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import kr.co.inogard.enio.api.Application;
import kr.co.inogard.enio.api.domain.file.CmmFile;
import kr.co.inogard.enio.api.domain.pr.PrDto;
import kr.co.inogard.enio.api.domain.pr.PrFile;
import kr.co.inogard.enio.api.domain.pr.PrFileDto;
import kr.co.inogard.enio.api.domain.pr.PrItem;
import kr.co.inogard.enio.api.domain.pr.PrSrv;
import kr.co.inogard.enio.api.mappers.file.FileMapper;
import kr.co.inogard.enio.api.mappers.pr.PrFileMapper;
import kr.co.inogard.enio.api.mappers.pr.PrItemMapper;
import kr.co.inogard.enio.api.pr.PrTestFixture;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@Slf4j
public class PrItemMapperTest {

	@Autowired
	private PrItemMapper prItemMapper;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	PrTestFixture prTestFixture;
	
	@Test
	public void createPrItemTest() throws ParseException {
		PrDto.Create create = prTestFixture.getPrCreateFixture();
		List<PrItem> itemList = modelMapper.map(create.getItemList(), new TypeToken<List<PrItem>>() {}.getType());
		for (PrItem prItem : itemList) {
			prItemMapper.add(prItem);
		}
	}
	
}
