package kr.co.inogard.enio.api.commons.filter.wrapper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class CaptureResponseWrapper extends HttpServletResponseWrapper {

  private ServletOutputStream outputStream;
  private PrintWriter writer;
  private CaptureOutputStream captureOutputStream;

  public CaptureResponseWrapper(HttpServletResponse response) throws IOException {
    super(response);
  }

  @Override
  public ServletOutputStream getOutputStream() throws IOException {
    if (writer != null) {
      throw new IllegalStateException("getWriter() has already been called on this response.");
    }

    if (outputStream == null) {
      outputStream = getResponse().getOutputStream();
      captureOutputStream = new CaptureOutputStream(outputStream);
    }

    return captureOutputStream;
  }

  @Override
  public PrintWriter getWriter() throws IOException {
    if (outputStream != null) {
      throw new IllegalStateException(
          "getOutputStream() has already been called on this response.");
    }

    if (writer == null) {
      captureOutputStream = new CaptureOutputStream(getResponse().getOutputStream());
      writer = new PrintWriter(
          new OutputStreamWriter(captureOutputStream, getResponse().getCharacterEncoding()), true);
    }

    return writer;
  }

  @Override
  public void flushBuffer() throws IOException {
    if (writer != null) {
      writer.flush();
    } else if (outputStream != null) {
      captureOutputStream.flush();
    }
  }

  public byte[] getByteArray() {
    if (captureOutputStream != null) {
      return captureOutputStream.getByteArray();
    } else {
      return new byte[0];
    }
  }

  private class CaptureOutputStream extends ServletOutputStream {
    private OutputStream outputStream;
    private ByteArrayOutputStream byteArrayOutputStream;

    public CaptureOutputStream(OutputStream outputStream) {
      this.outputStream = outputStream;
      this.byteArrayOutputStream = new ByteArrayOutputStream(1024);
    }

    @Override
    public void write(int b) throws IOException {
      outputStream.write(b);
      byteArrayOutputStream.write(b);
    }

    public byte[] getByteArray() {
      return byteArrayOutputStream.toByteArray();
    }

    @Override
    public boolean isReady() {
      // TODO Auto-generated method stub
      return false;
    }

    @Override
    public void setWriteListener(WriteListener arg0) {
      // TODO Auto-generated method stub
      
    }
  }
}
