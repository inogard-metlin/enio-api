package kr.co.inogard.enio.api.web;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/logs")
public class LogController {

  @Value("file:${logging.path}")
  private Resource loggingPathResource;

  @RequestMapping(method = RequestMethod.GET)
  public ModelAndView getLogs() {
    ModelAndView model = new ModelAndView("logs/list");
    SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    List<Map<String, Object>> logFiles = new ArrayList<Map<String, Object>>();
    List<String> logMonths = new ArrayList<String>();
    try {
      File rootPath = loggingPathResource.getFile();
      if (rootPath.exists() && rootPath.isDirectory()) {
        for (File monthPath : rootPath.listFiles()) {
          logMonths.add(monthPath.getName());
          if (monthPath.exists() && monthPath.isDirectory()) {
            for (File file : monthPath.listFiles()) {
              Map<String, Object> fileInfo = new HashMap<String, Object>();
              fileInfo.put("monthPath", monthPath.getName());
              fileInfo.put("fileName", file.getName());
              fileInfo.put("lastModified", sf.format(file.lastModified()));
              fileInfo.put("fileSize", (file.length() / 1024) + "KB");
              logFiles.add(fileInfo);
            }
          }
        }
      }
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    }

    model.addObject("logMonths", logMonths);
    model.addObject("logFiles", logFiles);
    return model;
  }

  @RequestMapping(value = "{monthPath}/files", method = RequestMethod.GET)
  public void getLogFile(@PathVariable("monthPath") String monthPath,
      @RequestParam("fileName") String fileName, HttpServletResponse response) {
    BufferedInputStream is = null;
    try {
      File rootPath = loggingPathResource.getFile();
      log.debug("fileName : {}", fileName);
      // fileName = fileName + ".log";
      File file = new File(rootPath, monthPath + File.separator + fileName);

      is = new BufferedInputStream(new FileInputStream(file));
      response.setContentType("application/octet-stream");
      response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
      response.setHeader("Content-Transfer-Encoding", "binary");

      IOUtils.copy(is, response.getOutputStream());
      response.flushBuffer();

      is.close();
    } catch (IOException ex) {
      log.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
      throw new RuntimeException("IOError writing file to output stream");
    } finally {
      IOUtils.closeQuietly(is);
    }
  }
}
