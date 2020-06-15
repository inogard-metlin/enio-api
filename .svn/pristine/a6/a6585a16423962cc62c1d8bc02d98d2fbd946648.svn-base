package kr.co.inogard.enio.api.commons;

import java.io.IOException;

import org.springframework.core.io.ByteArrayResource;

public class MultipartFileResource extends ByteArrayResource {

	private final String filename;

	public MultipartFileResource(byte[] payload, String originalFileName) throws IOException {
		super(payload);
		this.filename = originalFileName;
	}

	@Override
	public String getFilename() {
		return this.filename;
	}

}