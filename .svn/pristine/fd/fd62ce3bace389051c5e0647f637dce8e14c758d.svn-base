package kr.co.inogard.enio.api.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import kr.co.inogard.enio.api.commons.constant.EnioServerType;
import kr.co.inogard.enio.api.domain.agent.Agent;


public class ApiUserDetailsImpl extends User {
  final private Agent agt;
  final private String rawLicenceKey;

  public ApiUserDetailsImpl(Agent agt, String password, EnioServerType enioServerType) {
    super(agt.getAgtCd(), password, authorities(agt, enioServerType));
    this.agt = agt;
    this.rawLicenceKey = password;
  }

  private static Collection<? extends GrantedAuthority> authorities(Agent agt, EnioServerType enioServerType) {
    List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
    if (enioServerType != null) {
      if (EnioServerType.AGENT != enioServerType) {
        authorities.add(new SimpleGrantedAuthority("ROLE_EBIZ4U"));
      } else {
        authorities.add(new SimpleGrantedAuthority("ROLE_API"));
      }
    } else {
      authorities.add(new SimpleGrantedAuthority("ROLE_EBIZ4U"));
    }
    
    return authorities;
  }

  public Agent getAgent() {
    return this.agt;
  }

  public String getRawLicenceKey() {
    return this.rawLicenceKey;
  }
}
