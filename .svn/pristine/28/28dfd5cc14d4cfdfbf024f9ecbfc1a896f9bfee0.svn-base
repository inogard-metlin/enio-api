package kr.co.inogard.enio.api.event;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import kr.co.inogard.enio.api.Application;
import kr.co.inogard.enio.api.commons.constant.EvtIOType;
import kr.co.inogard.enio.api.commons.constant.EvtSt;
import kr.co.inogard.enio.api.commons.constant.ScheKind;
import kr.co.inogard.enio.api.domain.event.CmmEvent;
import kr.co.inogard.enio.api.domain.event.CmmEventDto;
import kr.co.inogard.enio.api.mappers.event.EventMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class EventMapperTest {

  @Autowired
  private EventMapper eventMapper;

  private CmmEvent cmmEvent1;
  private CmmEvent cmmEvent2;
  private CmmEvent cmmEvent3;

  @Before
  public void setUp() {
    cmmEvent1 = new CmmEvent();
    cmmEvent1.setEvtIoType(EvtIOType.IN.name());
    cmmEvent1.setEvtCmdParam("TEST1");
    cmmEvent1.setEvtSt(EvtSt.W.name());
    cmmEvent1.setReqCntn("TEST1");
    cmmEvent1.setAgtCd("00000");
    cmmEvent1.setScheKind(ScheKind.S.name());

    cmmEvent2 = new CmmEvent();
    cmmEvent2.setEvtIoType(EvtIOType.IN.name());
    cmmEvent2.setEvtCmdParam("TEST2");
    cmmEvent2.setEvtSt(EvtSt.W.name());
    cmmEvent2.setReqCntn("TEST2");
    cmmEvent2.setAgtCd("00000");
    cmmEvent2.setScheKind(ScheKind.S.name());

    cmmEvent3 = new CmmEvent();
    cmmEvent3.setEvtIoType(EvtIOType.IN.name());
    cmmEvent3.setEvtCmdParam("TEST3");
    cmmEvent3.setEvtSt(EvtSt.W.name());
    cmmEvent3.setReqCntn("TEST3");
    cmmEvent3.setAgtCd("00000");
    cmmEvent3.setScheKind(ScheKind.S.name());
  }

  @Test
  public void add() {
    // Given
    // When
    eventMapper.add(cmmEvent1);

    // Then
    CmmEvent cmmEvent = eventMapper.findByEvtNo(cmmEvent1.getEvtNo());
    log.debug("reqCntn : {}", cmmEvent.getReqCntn());
    assertThat(cmmEvent.getReqCntn(), is(cmmEvent1.getReqCntn()));
  }

  @Test
  public void getAll() {
    // Given
    // When
    eventMapper.add(cmmEvent1);
    eventMapper.add(cmmEvent2);
    eventMapper.add(cmmEvent3);

    // Then
    CmmEventDto.Search search = new CmmEventDto.Search();
    search.setAgtCd("00000");
    assertThat(eventMapper.findAll(search, null).size(), is(3));
  }

  @Test
  public void getAllByDataTablesInput() {
    // Given
    DataTablesInput mockDataTablesInput = mock(DataTablesInput.class);
    Mockito.when(mockDataTablesInput.getStart()).thenReturn(0);
    Mockito.when(mockDataTablesInput.getLength()).thenReturn(10);

    // When
    eventMapper.add(cmmEvent1);
    eventMapper.add(cmmEvent2);
    eventMapper.add(cmmEvent3);

    // Then
    CmmEventDto.Search search = new CmmEventDto.Search();
    verify(mockDataTablesInput, times(2)).getStart();
    assertThat(eventMapper
        .findAll(search, new PageRequest(0, 20, Direction.fromString("desc"), "reg_dt")).size(),
        is(3));
  }

  @Test
  public void getAllByOrders() {
    // Given
    // When
    eventMapper.add(cmmEvent1);
    eventMapper.add(cmmEvent2);
    eventMapper.add(cmmEvent3);

    // Then
    CmmEventDto.Search search = new CmmEventDto.Search();
    assertThat(eventMapper
        .findAll(search, new PageRequest(0, 20, Direction.fromString("desc"), "reg_dt")).size(),
        is(3));
  }

  @Test
  public void update() {
    // Given
    // When
    eventMapper.add(cmmEvent1);
    cmmEvent2.setEvtNo(cmmEvent1.getEvtNo());
    cmmEvent2.setRcvCntn("TEST2");
    eventMapper.updateRes(cmmEvent2);

    // Then
    assertThat(eventMapper.findByEvtNo(cmmEvent1.getEvtNo()).getRcvCntn(),
        is(cmmEvent2.getReqCntn()));
  }

}
