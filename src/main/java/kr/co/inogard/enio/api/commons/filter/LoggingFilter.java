package kr.co.inogard.enio.api.commons.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.inogard.enio.api.commons.constant.EnioMediaType;
import kr.co.inogard.enio.api.commons.constant.EvtIOType;
import kr.co.inogard.enio.api.commons.constant.EvtSt;
import kr.co.inogard.enio.api.commons.constant.RsltCd;
import kr.co.inogard.enio.api.commons.constant.ScheKind;
import kr.co.inogard.enio.api.commons.filter.wrapper.CaptureRequestWrapper;
import kr.co.inogard.enio.api.commons.filter.wrapper.CaptureResponseWrapper;
import kr.co.inogard.enio.api.commons.util.CryptoUtil;
import kr.co.inogard.enio.api.commons.util.Utils;
import kr.co.inogard.enio.api.domain.event.CmmEvent;
import kr.co.inogard.enio.api.domain.event.CmmEventCmdParam;
import kr.co.inogard.enio.api.domain.event.CmmEventReqContent;
import kr.co.inogard.enio.api.domain.event.CmmEventResContent;
import kr.co.inogard.enio.api.security.ApiUserDetailsImpl;
import kr.co.inogard.enio.api.service.event.EventService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LoggingFilter implements Filter {

  @Autowired
  private EventService eventService;

  @Autowired
  private CryptoUtil cryptoUtil;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // TODO Auto-generated method stub
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    log.info("============================ Request Logging START ============================");
    CaptureRequestWrapper captureRequestWrapper =
        new CaptureRequestWrapper((HttpServletRequest) request);
    CaptureResponseWrapper captureResponseWrapper =
        new CaptureResponseWrapper((HttpServletResponse) response);

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    log.debug("authentication : {}", authentication);

    String agtCd = ((ApiUserDetailsImpl) authentication.getPrincipal()).getAgent().getAgtCd();
    String cryptoKey = cryptoUtil.getCryptoKey();
    log.debug("agtCd : {}", agtCd);
    log.debug("cryptoKey : {}", cryptoKey);

    MediaType enioJsonMediaType = EnioMediaType.APPLICATION_ENIO_JSON_UTF8.getMediaType();
    String newEvtNo = null;
    String evtCmdParam = null;
    String reqCntn = null;
    String rsltCd = null;
    String rsltMsg = null;
    String rcvCntn = null;

    try {
      Enumeration<String> headerNames = captureRequestWrapper.getHeaderNames();
      Map<String, String> headers = new HashMap<String, String>();
      while (headerNames.hasMoreElements()) {
        String key = headerNames.nextElement();
        String value = captureRequestWrapper.getHeader(key);
        headers.put(key, value);
      }

      String uri = captureRequestWrapper.getRequestURL().toString();
      String method = captureRequestWrapper.getMethod().toString();
      String contentType = captureRequestWrapper.getContentType();
      String bodyStr = Utils.getStringFromInputStream(captureRequestWrapper.getInputStream());

      log.info("Request uri : {}", uri);
      log.info("Request mehtod : {}", method);
      log.info("Request headers : {}", headers);
      log.info("Request contentType : {}", contentType);
      log.debug("Request body : {}", bodyStr);

      List<Map<String, String>> partHeaderList = new ArrayList<Map<String, String>>();
      if (StringUtils.hasText(contentType)) {
        MediaType mediaType = MediaType.valueOf(contentType);

        boolean isApplicationEnioJson = enioJsonMediaType.isCompatibleWith(mediaType);
        boolean isMultipartFormData = MediaType.MULTIPART_FORM_DATA.isCompatibleWith(mediaType);

        log.info("isApplicationEnioJson : {}", isApplicationEnioJson);
        log.info("isMultipartFormData : {}", isMultipartFormData);

        if (isApplicationEnioJson || isMultipartFormData) {
          String encryptedEnioJsonStr = bodyStr;
          if (isMultipartFormData) {
            Collection<Part> parts = captureRequestWrapper.getParts();
            for (Part part : parts) {
              Collection<String> partHeaderNames = part.getHeaderNames();
              Map<String, String> partHeaders = new HashMap<String, String>();
              for (String headerName : partHeaderNames) {
                partHeaders.put(headerName, part.getHeader(headerName));
              }
              partHeaderList.add(partHeaders);
              if (enioJsonMediaType.isCompatibleWith(MediaType.valueOf(part.getContentType()))) {
                encryptedEnioJsonStr = Utils.getStringFromInputStream(part.getInputStream());
              }
            }
          }

          log.debug("encryptedEnioJsonStr : {}", encryptedEnioJsonStr);
          if (encryptedEnioJsonStr != null) {
            bodyStr = cryptoUtil.decryptString(cryptoKey, encryptedEnioJsonStr);
          }
        }
      }

      Object bodyObj = null;
      try {
        bodyObj = objectMapper.readValue(bodyStr, Object.class);
      } catch (Exception e) {
        bodyObj = bodyStr;
      }

      CmmEventCmdParam cmmEventCmdParam = new CmmEventCmdParam();
      cmmEventCmdParam.setUri(uri);
      cmmEventCmdParam.setHttpMethod(method);

      CmmEventReqContent cmmEventReqContent = new CmmEventReqContent();
      cmmEventReqContent.setHeaders(headers);
      cmmEventReqContent.setPartHeaderList(partHeaderList);
      cmmEventReqContent.setContent(bodyObj);

      evtCmdParam = objectMapper.writeValueAsString(cmmEventCmdParam);
      reqCntn = objectMapper.writeValueAsString(cmmEventReqContent);

      log.debug("evtCmdParam : {}", evtCmdParam);
      log.debug("reqCntn : {}", reqCntn);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      StringWriter errors = new StringWriter();
      e.printStackTrace(new PrintWriter(errors));
      rsltCd = RsltCd.ERR0000.name();
      rsltMsg = e.getMessage();
      rcvCntn = errors.toString();

      if (e instanceof IOException) {
        throw (IOException) e;
      } else if (e instanceof ServletException) {
        throw (ServletException) e;
      } else {
        throw new RuntimeException(e);
      }
    } finally {
      try {
        CmmEvent evt = new CmmEvent();
        evt.setEvtIoType(EvtIOType.IN.name());
        evt.setEvtCmdParam(evtCmdParam);
        evt.setEvtSt(EvtSt.W.name());
        evt.setReqCntn(reqCntn);
        evt.setAgtCd(agtCd);
        evt.setScheKind(ScheKind.S.name());

        eventService.create(evt);
        newEvtNo = evt.getEvtNo();
        log.info("newEvtNo : {}", newEvtNo);

        if (StringUtils.hasText(newEvtNo) && StringUtils.hasText(rsltCd)
            && RsltCd.ERR0000.name().equals(rsltCd)) {
          CmmEvent resEvt = new CmmEvent();
          resEvt.setEvtNo(newEvtNo);
          resEvt.setEvtSt(EvtSt.C.name());
          resEvt.setRsltCd(rsltCd);
          resEvt.setRsltMsg(rsltMsg);
          resEvt.setRcvCntn(rcvCntn);
          eventService.updateRes(evt);
        }
      } catch (Exception e) {
        log.error("Request Logging Data Input Exception", e);
      }
      log.info("============================ Request Logging END ============================");
    }

    try {
      chain.doFilter(captureRequestWrapper, captureResponseWrapper);
      captureResponseWrapper.flushBuffer();
    } finally {
      log.info("============================ Response Logging START ============================");
      try {
        Collection<String> headerNames = captureResponseWrapper.getHeaderNames();
        Map<String, String> headers = new HashMap<String, String>();
        for (String headerName : headerNames) {
          headers.put(headerName, captureResponseWrapper.getHeader(headerName));
        }

        int status = captureResponseWrapper.getStatus();
        String contentType = captureResponseWrapper.getContentType();
        String bodyStr = new String(captureResponseWrapper.getByteArray(),
            captureResponseWrapper.getCharacterEncoding());

        log.info("Response headers : {}", headers);
        log.info("Response statusCode : {}", status);
        log.info("Response contentType : {}", contentType);
        log.debug("Response body : {}", bodyStr);

        rsltCd = RsltCd.UNKNOWN.name();
        rsltMsg = RsltCd.UNKNOWN.getCodeNm();
        if (StringUtils.hasText(contentType)) {
          MediaType resMediaType = MediaType.valueOf(contentType);
          boolean isApplicationEnioJson = enioJsonMediaType.isCompatibleWith(resMediaType);
          log.info("isApplicationEnioJson : {}", isApplicationEnioJson);

          if (isApplicationEnioJson) {
            bodyStr = cryptoUtil.decryptString(cryptoKey, bodyStr);
            log.debug("decryptedEnioJsonStr : {}", bodyStr);
            Map<String, Object> resBodyMap =
                objectMapper.readValue(bodyStr, new TypeReference<Map<String, Object>>() {});
            rsltCd = (String) resBodyMap.get("rsltCd");
            rsltMsg = (String) resBodyMap.get("rsltMsg");
          }
        }

        Object bodyObj = null;
        try {
          bodyObj = objectMapper.readValue(bodyStr, Object.class);
        } catch (Exception e) {
          bodyObj = bodyStr;
        }

        CmmEventResContent cmmEventResContent = new CmmEventResContent();
        cmmEventResContent.setHeaders(headers);
        cmmEventResContent.setStatus(status);
        cmmEventResContent.setContent(bodyObj);

        rcvCntn = objectMapper.writeValueAsString(cmmEventResContent);
        log.debug("rcvCntn : {}", rcvCntn);
      } catch (Exception e) {
        log.error(e.getMessage(), e);
        StringWriter errors = new StringWriter();
        e.printStackTrace(new PrintWriter(errors));
        rsltCd = RsltCd.ERR1000.name();
        rsltMsg = e.getMessage();
        rcvCntn = errors.toString();

        if (e instanceof IOException) {
          throw (IOException) e;
        } else if (e instanceof ServletException) {
          throw (ServletException) e;
        } else {
          throw new RuntimeException(e);
        }
      } finally {
        try {
          log.info("newEvtNo : {}", newEvtNo);
          if (StringUtils.hasText(newEvtNo)) {
            CmmEvent evt = new CmmEvent();
            evt.setEvtNo(newEvtNo);
            evt.setEvtSt(EvtSt.C.name());
            evt.setRsltCd(rsltCd);
            evt.setRsltMsg(rsltMsg);
            evt.setRcvCntn(rcvCntn);
            eventService.updateRes(evt);
          }
        } catch (Exception e) {
          log.error("Response Logging Data Input Exception", e);
        }
        log.info("============================ Response Logging END ============================");
      }
    }
  }

  @Override
  public void destroy() {
    // TODO Auto-generated method stub
  }

}
