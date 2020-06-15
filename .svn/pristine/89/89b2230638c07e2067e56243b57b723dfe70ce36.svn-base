package kr.co.inogard.enio.api.security;

import java.util.Enumeration;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.stereotype.Service;
import kr.co.inogard.enio.api.commons.constant.EnioHeader;
import kr.co.inogard.enio.api.commons.constant.EnioServerType;
import kr.co.inogard.enio.api.domain.agent.Agent;
import kr.co.inogard.enio.api.mappers.agent.AgentMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ApiUserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private AgentMapper agentMapper;
  
  @Autowired
  private HttpServletRequest request;

  @Override
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    Agent agt = agentMapper.findAgentByAgtCd(userName);
    
    EnioServerType enioServerType = null;
    try {
      enioServerType = EnioServerType.valueOf(request.getHeader(EnioHeader.EnioServerType.getName()));
    } catch(Exception e) {
      ;
    }
    
    if (agt == null) {
      throw new UsernameNotFoundException(userName);
    }

    String rawLicenceKey = null;
    try {
      rawLicenceKey = new String(Base64.decode(agt.getLicenceKey().getBytes()), "UTF-8");
      log.info("Decode Licence Key : {}", rawLicenceKey);
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new UsernameNotFoundException("Invalid Licence Key");
    }

    return new ApiUserDetailsImpl(agt, rawLicenceKey, enioServerType);
  }
}
