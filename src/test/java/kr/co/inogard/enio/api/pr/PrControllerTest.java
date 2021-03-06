package kr.co.inogard.enio.api.pr;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.fileUpload;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.request;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.Validator;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.inogard.enio.api.Application;
import kr.co.inogard.enio.api.commons.EnioContext;
import kr.co.inogard.enio.api.commons.MultipartFileResource;
import kr.co.inogard.enio.api.commons.constant.EnioMediaType;
import kr.co.inogard.enio.api.commons.domain.ErrorResponse;
import kr.co.inogard.enio.api.commons.handler.EnioValidateHandler;
import kr.co.inogard.enio.api.commons.util.CryptoUtil;
import kr.co.inogard.enio.api.domain.ftp.FtpFileDto;
import kr.co.inogard.enio.api.domain.pr.PrDto;
import kr.co.inogard.enio.api.service.ftp.FtpService;
import kr.co.inogard.enio.api.service.pr.PrService;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@Slf4j
public class PrControllerTest {

  MockMvc mockMvc;

  @Autowired
  WebApplicationContext wac;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  CryptoUtil cryptoUtil;

  @Autowired
  PrService prService;

  @Autowired
  Validator validator;

  @Autowired
  FtpService ftpService;

  @Autowired
  private EnioValidateHandler enioValidateHandler;

  @Autowired
  PrTestFixture prTestFixture;

  @Autowired
  RestTemplate restTemplate;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
  }

  @Test
  public void createPr() throws Exception {
    PrDto.Create createDto = prTestFixture.getPrCreateFixture();

    HttpHeaders jsonPartHeader = new HttpHeaders();
    jsonPartHeader.setContentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());
    HttpEntity<PrDto.Create> jsonPart = new HttpEntity<PrDto.Create>(createDto, jsonPartHeader);

    MockMultipartFile file =
        new MockMultipartFile("file1", "filename1.txt", "text/plain", "my-file1".getBytes());
    MockMultipartFile file2 =
        new MockMultipartFile("file2", "filename2.txt", "text/plain", "my-file1".getBytes());

    MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<String, Object>();
    multiValueMap.add("data", jsonPart);
    multiValueMap.add("files", new MultipartFileResource(file.getBytes(), "filename1.txt"));
    multiValueMap.add("files", new MultipartFileResource(file2.getBytes(), "filename2.txt"));

    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
    acceptableMediaTypes.add(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());
    headers.setAccept(acceptableMediaTypes);

    HttpEntity<MultiValueMap<String, Object>> requestEntity =
        new HttpEntity<MultiValueMap<String, Object>>(multiValueMap, headers);

    EnioContext.local.set(new EnioContext("S0037", "ENIOS0037-201708"));
    ErrorResponse res = null;
    try {
      res = restTemplate.postForObject("http://localhost:8080/api/pr", requestEntity,
          ErrorResponse.class);
    } catch (HttpStatusCodeException e) {
      e.getResponseBodyAsString();
      log.debug("error : {}", e.getResponseBodyAsString());
    }
    EnioContext.local.remove();
    log.debug("res : {}", res);
  }

  @Test
  public void listTest() throws Exception {
    EnioContext.local.set(new EnioContext("S0037", "ENIOS0037-201708"));
    ErrorResponse res = null;


    HttpHeaders header = new HttpHeaders();
    List<Map<String, String>> infoList = new ArrayList<Map<String, String>>();
    Map<String, String> info = new HashMap<String, String>();
    info.put("name", "sunj");
    infoList.add(info);

    header.setContentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());
    header.setAccept(
        Collections.singletonList(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType()));
    HttpEntity<List<Map<String, String>>> requestEntity =
        new HttpEntity<List<Map<String, String>>>(infoList, header);
    try {
      res = restTemplate.postForObject("http://localhost:8080/api/pr/test2", requestEntity,
          ErrorResponse.class);
    } catch (HttpStatusCodeException e) {
      e.getResponseBodyAsString();
      log.debug("error : {}", e.getResponseBodyAsString());
    }
    EnioContext.local.remove();
  }

  @Test
  public void createPrMockMvc() throws Exception {
    try {
      PrDto.Create createDto = prTestFixture.getPrCreateFixture();
      String encryptString =
          cryptoUtil.encryptString("ENIOS0037-201708", objectMapper.writeValueAsString(createDto));
      ResultActions result = mockMvc.perform(fileUpload("/api/pr")
          .contentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType())
          .content(encryptString).accept(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType())
          .locale(Locale.KOREA));
      result.andDo(print());
    } catch (HttpClientErrorException e) {
      e.getResponseBodyAsString();
      log.debug("error : {}", e.getResponseBodyAsString());
    }
  }


  @Test
  public void prCancelTest() throws Exception {
    EnioContext.local.set(new EnioContext("S0037", "ENIOS0037-201708"));
    PrDto.ReqCancelCreate a = new PrDto.ReqCancelCreate();
    a.setPrNo("12312312312");

    String encryptString =
        cryptoUtil.encryptString("ENIOS0037-201708", objectMapper.writeValueAsString(a));

    mockMvc.perform(request(HttpMethod.PATCH, "/api/pr/12312312/cancel")
        .contentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType())
        .content(encryptString));
    EnioContext.local.remove();
  }

  @Test
  public void validTest() throws Exception {
    PrDto.Create createDto = prTestFixture.getPrCreateFixture();
    createDto.setPrNo("");
    enioValidateHandler.validate(createDto);
  }

  @Test
  public void ftpServiceTest() throws Exception {
    /*
     * FtpFileDto.Store store = new FtpFileDto.Store(); store.setRemoteFilePath("/tmpe22222");
     * store.setRemoteFileName("test111.txt"); store.setFile(new File("d:/test/test111.txt"));
     * List<FtpFileDto.Store> storeList = new ArrayList<FtpFileDto.Store>(); storeList.add(store);
     * ftpService.store(storeList);
     * 
     * FtpFileDto.Retrieve retrieve = new FtpFileDto.Retrieve();
     * retrieve.setRemoteFilePath("/tmpe22222"); retrieve.setRemoteFileName("test111.txt");
     * retrieve.setFile(new File("d:/test2/test111.txt"));
     * 
     * ftpService.retrieve(retrieve);
     */

    FtpFileDto.Delete delete = new FtpFileDto.Delete();
    delete.setRemoteFilePath("/tmpe22222");
    delete.setRemoteFileName("test111.txt");
    ftpService.delete(delete);
  }

  @Test
  public void jacksonConvertTest()
      throws JsonParseException, JsonMappingException, IOException, ParseException {
    SimpleDateFormat formatterYMD = new SimpleDateFormat("yyyyMMdd");
    PrDto.Create create = new PrDto.Create();
    create.setDlvFrYmd(formatterYMD.parse("20170509"));
    log.debug("create.getDlvFrYmd() {}", create.getDlvFrYmd());
    log.debug("objectMapper.writeValueAsString(create) {}",
        objectMapper.writeValueAsString(create));

    PrDto.Create create2 =
        objectMapper.readValue(objectMapper.writeValueAsString(create), PrDto.Create.class);
    log.debug("create2.getDlvFrYmd() {}", create2.getDlvFrYmd());
  }

  @Test
  public void aopTest() throws Exception {
    // customerBo.addCustomerAround("TEST");
    ResultActions result = mockMvc.perform(get("/api/pr")).andDo(print());
    ResultActions result2 = mockMvc.perform(get("/admin/test")).andDo(print());
  }

  @Test
  public void test() throws JsonProcessingException {
    EnioContext.local.set(new EnioContext("S0037", "ENIOS0037-201708"));
    try {
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());

      List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
      acceptableMediaTypes.add(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());
      headers.setAccept(acceptableMediaTypes);

      // Map<String, Object> data = new HashMap<String, Object>();
      // data.put("name", "sunj");

      PrDto.Create createDto = new PrDto.Create();
      createDto.setPrNo("01234567890123456789");

      HttpEntity<String> entity =
          new HttpEntity<String>(objectMapper.writeValueAsString(createDto), headers);
      // ResponseEntity<String> response =
      // restTemplate.exchange("http://localhost:8080/api/pr/test", HttpMethod.POST, entity,
      // String.class);

      // String res =
      // restTemplate.postForObject("http://localhost:8080/api/pr/test",
      // entity, String.class);
    } catch (HttpStatusCodeException e) {
      e.getResponseBodyAsString();
      log.debug("error : {}", e.getResponseBodyAsString());
    }
    EnioContext.local.remove();
  }

  @Test
  public void test2() throws Exception {
    EnioContext.local.set(new EnioContext("S0037", "ENIOS0037-201708"));
    try {
      PrDto.Create createDto = new PrDto.Create();
      createDto.setPrNo("01234567890123456789");

      HttpHeaders jsonPartHeader = new HttpHeaders();
      // jsonPartHeader.setContentType(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType());
      HttpEntity<PrDto.Create> jsonPart = new HttpEntity<PrDto.Create>(createDto, jsonPartHeader);

      MockMultipartFile file =
          new MockMultipartFile("file1", "filename1.txt", "text/plain", "my-file1".getBytes());
      MockMultipartFile file2 =
          new MockMultipartFile("file2", "filename2.txt", "text/plain", "my-file1".getBytes());

      MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<String, Object>();
      multiValueMap.add("data", jsonPart);
      multiValueMap.add("files", new MultipartFileResource(file.getBytes(), "filename1.txt"));
      multiValueMap.add("files", new MultipartFileResource(file2.getBytes(), "filename2.txt"));

      HttpHeaders requestHeaders = new HttpHeaders();
      requestHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
      // requestHeaders.setAccept(Collections.singletonList(EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType()));

      HttpEntity<MultiValueMap<String, Object>> requestEntity =
          new HttpEntity<MultiValueMap<String, Object>>(multiValueMap, requestHeaders);

      // PrDto.Create res = restTemplate.postForObject("http://localhost:8080/api/pr",
      // requestEntity, PrDto.Create.class);
      // log.debug("res : {}", res);
    } catch (HttpStatusCodeException e) {
      e.getResponseBodyAsString();
      log.debug("error : {}", e.getResponseBodyAsString());
    }
    EnioContext.local.remove();
  }

}
