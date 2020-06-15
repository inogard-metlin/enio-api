package kr.co.inogard.enio.api.pr;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import kr.co.inogard.enio.api.Application;
import kr.co.inogard.enio.api.domain.pr.Pr;
import kr.co.inogard.enio.api.service.pr.PrService;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@Slf4j
public class PrServiceTest {

  @Autowired
  PrService prService;

  @Autowired
  PrTestFixture prTestFixture;

  @Test
  public void createPrTest() throws ParseException {
    MultipartFile file =
        new MockMultipartFile("file1", "filename1.txt", "text/plain", "my-file1".getBytes());
    MultipartFile file2 =
        new MockMultipartFile("file2", "filename2.txt", "text/plain", "my-file2".getBytes());
    List<MultipartFile> files = new ArrayList<MultipartFile>();
    files.add(file);
    files.add(file2);

    Pr pr = prService.create(prTestFixture.getPrCreateFixture(), files);
    log.debug("Create Pr : {}", pr);
  }

  @Test
  public void getPrTest() throws ParseException {
    String prNo = "S0037102017092600001";
    Pr pr = prService.getPr(prNo);
    log.debug("pr.ttl:{}", pr.getTtl());
    log.debug("pr.itemList.size:{}", pr.getItemList().size());
    log.debug("pr.fileList.size:{}", pr.getFileList().size());
    log.debug("pr.itemList(0).srvList:{}", pr.getItemList().get(0).getSrvList());



  }

}
