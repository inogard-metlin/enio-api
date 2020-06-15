package kr.co.inogard.enio.api.service.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import org.apache.commons.net.ftp.FTPClient;
import kr.co.inogard.enio.api.commons.exception.FtpException;
import kr.co.inogard.enio.api.domain.ftp.FtpFileDto;

public class FtpServiceImpl implements FtpService {

  private FTPClient ftpClient;

  public void setFtpClient(FTPClient ftpClient) {
    this.ftpClient = ftpClient;
  }

  @Override
  public void store(FtpFileDto.Store store) {
    InputStream in = null;

    try {
      makeDirectory(store.getRemoteFilePath());
      if (!ftpClient.storeFile(
          store.getRemoteFilePath() + File.separator + store.getRemoteFileName(),
          in = new FileInputStream(store.getFile()))) {
        throw new FtpException("Failed to store file");
      }
    } catch (Exception e) {
      throw new FtpException(e);
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (Exception iex) {
        }
      }
    }
  }

  @Override
  public void store(List<FtpFileDto.Store> storeList) {
    if (storeList == null || storeList.isEmpty()) {
      throw new FtpException("No files to store.");
    }

    for (FtpFileDto.Store store : storeList) {
      store(store);
    }
  }

  @Override
  public void retrieve(FtpFileDto.Retrieve retrieve) {
    OutputStream out = null;

    try {
      ftpClient.retrieveFile(
          retrieve.getRemoteFilePath() + File.separator + retrieve.getRemoteFileName(),
          out = new FileOutputStream(retrieve.getFile()));
    } catch (Exception e) {
      throw new FtpException(e);
    } finally {
      if (out != null) {
        try {
          out.close();
        } catch (Exception iex) {
        }
      }
    }
  }

  @Override
  public void retrieve(List<FtpFileDto.Retrieve> retrieveList) {
    if (retrieveList == null || retrieveList.isEmpty()) {
      throw new FtpException("No files to retrieve.");
    }

    for (FtpFileDto.Retrieve retrieve : retrieveList) {
      retrieve(retrieve);
    }
  }

  @Override
  public void delete(FtpFileDto.Delete delete) {
    try {
      ftpClient
          .deleteFile(delete.getRemoteFilePath() + File.separator + delete.getRemoteFileName());
    } catch (Exception e) {
      throw new FtpException(e);
    }
  }


  @Override
  public void delete(List<FtpFileDto.Delete> deleteList) {
    if (deleteList == null || deleteList.isEmpty()) {
      throw new FtpException("No files to delete.");
    }

    for (FtpFileDto.Delete delete : deleteList) {
      delete(delete);
    }
  }

  public boolean makeDirectory(String remotePath) {
    boolean bval;
    try {
      int start = 0;
      int idx = -1;
      String dir = remotePath;

      while (true) {
        idx = dir.indexOf("/", start);
        if (idx == -1) {
          bval = ftpClient.makeDirectory(dir);
          break;
        } else {
          start = idx + 1;
          String s = dir.substring(0, idx);
          if (s.length() > 0) {
            bval = ftpClient.makeDirectory(s);
          }
        }
      }
    } catch (Exception e) {
      throw new FtpException(e);
    }
    return bval;
  }
}
