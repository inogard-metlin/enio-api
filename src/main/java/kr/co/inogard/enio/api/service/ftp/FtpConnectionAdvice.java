package kr.co.inogard.enio.api.service.ftp;

import java.io.IOException;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import kr.co.inogard.enio.api.commons.exception.FtpException;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FtpConnectionAdvice implements MethodInterceptor {

	@Value("${enio.ftp.host}")
	private String ftpHost;

	@Value("${enio.ftp.port}")
	private String ftpPort;

	@Value("${enio.ftp.user}")
	private String ftpUser;

	@Value("${enio.ftp.password}")
	private String ftpPassword;
	
	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		FTPClient ftpClient = new FTPClient();
		try {
			log.debug("ftpHost : {}", ftpHost);
			log.debug("ftpPort : {}", ftpPort);
			log.debug("ftpUser : {}", ftpUser);
			log.debug("ftpPassword : {}", ftpPassword);
			
			ftpClient.connect(ftpHost, Integer.parseInt(ftpPort));
			String ftpReplyString = ftpClient.getReplyString();
			int ftpReplyCode = ftpClient.getReplyCode();

			log.debug("FTP Server Connected to {}", ftpHost);
			log.debug("FTP Server ReplyString : {}", ftpReplyString);
			log.debug("FTP Server ReplyCode : {}", ftpReplyCode);

			if (!FTPReply.isPositiveCompletion(ftpReplyCode)) {
				ftpClient.disconnect();
				throw new FtpException("FTP server refused connection.");
			}

			ftpClient.login(ftpUser, ftpPassword);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();

			((FtpServiceImpl) invocation.getThis()).setFtpClient(ftpClient);
			Object ret = invocation.proceed();
			return ret;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				if (ftpClient.isConnected()) {
					log.debug("FTP Logout");
					ftpClient.logout();
					log.debug("FTP disconnect");
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
}