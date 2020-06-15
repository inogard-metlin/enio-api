package kr.co.inogard.enio.api.commons;

import java.io.File;

import org.springframework.core.io.FileSystemResource;

public class EnioFileSystemResource extends FileSystemResource {

	private String fileName;
	public EnioFileSystemResource(File file,String cliFileNm) {
		super(file);
		this.fileName=cliFileNm;
	}

	public EnioFileSystemResource(String path,String cliFileNm) {
		super(path);
		this.fileName=cliFileNm;
		
	}

	@Override
	public String getFilename() {
		return this.fileName;
	}
	

}
