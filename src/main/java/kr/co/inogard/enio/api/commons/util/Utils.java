package kr.co.inogard.enio.api.commons.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.datatables.parameter.ColumnParameter;
import org.springframework.data.jpa.datatables.parameter.OrderParameter;
import org.springframework.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Utils {

  public static String getStringFromInputStream(InputStream is) {
    BufferedReader br = null;
    StringBuilder sb = new StringBuilder();

    String line;
    try {
      br = new BufferedReader(new InputStreamReader(is));
      while ((line = br.readLine()) != null) {
        sb.append(line);
      }
    } catch (IOException e) {
      log.error(e.getMessage(), e);
    } finally {
      if (br != null) {
        try {
          br.close();
        } catch (IOException e) {
          log.error(e.getMessage(), e);
        }
      }
    }
    return sb.toString();
  }

  public static Map<String, String> multipartHeadersToMap(String multipartHeaders) {
    Map<String, String> headers = new HashMap<String, String>();
    if (StringUtils.hasText(multipartHeaders)) {
      String[] headersArr = multipartHeaders.split("\r\n");
      for (String header : headersArr) {
        String[] headersMapArr = header.split(":");
        if (headersMapArr.length > 1) {
          String k = header.split(":")[0];
          String v = header.split(":")[1];

          if (StringUtils.hasText(k)) {
            k = k.trim();
          }

          if (StringUtils.hasText(v)) {
            v = v.trim();
          }

          headers.put(k, v);
        }
      }
    }
    return headers;
  }
  
  public static Pageable dataTablesInputToPageable(DataTablesInput input) {
    OrderParameter order = input.getOrder().get(0);
    ColumnParameter column = input.getColumns().get(order.getColumn());

    if (column.getOrderable()) {
      String sortColumn = column.getName();
      Direction sortDirection = Direction.fromString(order.getDir());

      return new PageRequest(input.getStart() / input.getLength(), input.getLength(), sortDirection,
          sortColumn);
    } else {
      return new PageRequest(input.getStart() / input.getLength(), input.getLength());
    }
  }
}
