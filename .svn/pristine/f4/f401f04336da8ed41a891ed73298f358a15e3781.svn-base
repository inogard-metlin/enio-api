package kr.co.inogard.enio.api.ftp;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import kr.co.inogard.enio.api.Application;
import kr.co.inogard.enio.api.commons.handler.EnioFileHandler;
import kr.co.inogard.enio.api.domain.ftp.FtpFileDto;
import kr.co.inogard.enio.api.service.ftp.FtpService;
import lombok.extern.slf4j.Slf4j;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
@Slf4j
public class FtpServiceTest {

	@Autowired
	private EnioFileHandler enioFileHandler;

	@Autowired
	private FtpService ftpService;

	@Test
	public void ftpStoreListTest() {
		List<FtpFileDto.Store> storeList = new ArrayList<FtpFileDto.Store>();
		FtpFileDto.Store store = new FtpFileDto.Store();
		store.setFile(new File("D:/temp/1.txt"));
		store.setRemoteFileName("remoteFileName-001");
		store.setRemoteFilePath("/enio/temp/");

		FtpFileDto.Store store2 = new FtpFileDto.Store();
		store2.setFile(new File("D:/temp/2.txt"));
		store2.setRemoteFileName("remoteFileName-002");
		store2.setRemoteFilePath("/enio/temp/");

		storeList.add(store2);
		storeList.add(store);
		ftpService.store(storeList);
	}

	@Test
	public void ftpRetrieveTest() {
		List<FtpFileDto.Retrieve> retrieveList = new ArrayList<FtpFileDto.Retrieve>();

		// 임시폴더 새엇ㅇ
		File tmpDir = enioFileHandler.createTempDirectory();
		tmpDir.mkdirs();
		FtpFileDto.Retrieve r = null;
		r = new FtpFileDto.Retrieve();
		r.setRemoteFilePath("/22/2014/04/15");
		r.setRemoteFileName("22201404150000000001.jpg");
		r.setFile(new File(tmpDir, "입찰_입찰_22201404150000000001.jpg"));
		retrieveList.add(r);

		r = new FtpFileDto.Retrieve();
		r.setRemoteFilePath("/22/2014/04/15");
		r.setRemoteFileName("22201404150000000002.jpg");
		r.setFile(new File(tmpDir, "입찰_입찰_22201404150000000002.jpg"));
		retrieveList.add(r);

		ftpService.retrieve(retrieveList);

	}

}
