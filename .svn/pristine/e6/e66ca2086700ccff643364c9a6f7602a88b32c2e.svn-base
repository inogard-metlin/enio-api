package kr.co.inogard.enio.api.commons.handler;

import java.io.File;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import kr.co.inogard.enio.api.commons.exception.EnioRunTimeException;

@Component
public class EnioFileHandler {

	@Value("${enio.file.temp-dir}")
	private String tempDir;

	public File createTempDirectory() {
		File file = new File(tempDir + File.separator + String.valueOf(System.currentTimeMillis()));
		if (!makeDirs(file)) {
			throw new EnioRunTimeException("Failed to make directory in temp directory");
		}
		return file;
	}

	public void cleanUpDirectory(File dir) {
		if (dir == null || !dir.isDirectory()) {
			return;
		}

		File[] fileList = dir.listFiles();
		if (dir.listFiles().length < 1) {
			deleteFile(dir);
		} else {
			for (File f : fileList) {
				deleteFile(f);
			}
		}
	}

	private boolean makeDirs(File dir) {
		if (dir == null) {
			return false;
		}

		return (dir.exists() || dir.mkdirs());
	}

	public void deleteFile(File f) {
		if (f != null) {
			f.delete();
		}
	}

}