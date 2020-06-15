package kr.co.inogard.enio.api.event;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.mapping.DataTablesOutput;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.inogard.enio.api.Application;
import kr.co.inogard.enio.api.commons.constant.EvtIOType;
import kr.co.inogard.enio.api.commons.constant.EvtSt;
import kr.co.inogard.enio.api.commons.constant.ScheKind;
import kr.co.inogard.enio.api.domain.event.CmmEvent;
import kr.co.inogard.enio.api.domain.event.CmmEventDto;
import kr.co.inogard.enio.api.service.event.EventService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class EventServiceTest {

  @Autowired
  private EventService eventService;

  @Autowired
  private ObjectMapper objectMapper;

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
  public void getAll() {
    // Given
    DataTablesInput mockDataTablesInput = mock(DataTablesInput.class);
    Mockito.when(mockDataTablesInput.getStart()).thenReturn(0);
    Mockito.when(mockDataTablesInput.getLength()).thenReturn(10);

    // When
    CmmEventDto.Search search = new CmmEventDto.Search();
    // search.setE4uIfSt("SR");
    DataTablesOutput<CmmEventDto.Response> result =
        eventService.getAll(search, mockDataTablesInput);

    // Then
    verify(mockDataTablesInput, times(2)).getStart();
    assertNotNull(result);

    log.debug("data : {}", result.getData());
  }

  @Test
  public void jsonViewTest() throws JsonProcessingException {
    // Given
    DataTablesInput mockDataTablesInput = mock(DataTablesInput.class);
    Mockito.when(mockDataTablesInput.getStart()).thenReturn(0);
    Mockito.when(mockDataTablesInput.getLength()).thenReturn(10);
    
    CmmEventDto.Search search = new CmmEventDto.Search();
    DataTablesOutput<CmmEventDto.Response> result =
        eventService.getAll(search, mockDataTablesInput);

    // When
    String result1 = objectMapper.writeValueAsString(result);

    // Then
    log.debug("result1 : {}", result1);
  }

}
