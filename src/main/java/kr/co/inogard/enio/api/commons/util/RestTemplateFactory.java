package kr.co.inogard.enio.api.commons.util;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.Collections;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import kr.co.inogard.enio.api.commons.converter.EnioJsonHttpMessageConverter;
import kr.co.inogard.enio.api.commons.handler.EnioResponseErrorHandler;

//@Component("restTemplateFactory")
public class RestTemplateFactory {

  //@Autowired
  EnioJsonHttpMessageConverter enioJsonHttpMessageConverter;

  //@Autowired
  ClientHttpRequestInterceptor restTemplateInterceptor;

  public RestTemplate create(String id, String pwd) {
    CloseableHttpClient client = HttpClientBuilder.create()
        .setDefaultCredentialsProvider(provider(id, pwd)).useSystemProperties().build();
    HttpComponentsClientHttpRequestFactory requestFactory =
        new HttpComponentsClientHttpRequestFactoryDigestAuth(client);

    RestTemplate restTemplate =
        new RestTemplate(new BufferingClientHttpRequestFactory(requestFactory));
    restTemplate.setInterceptors(Collections.singletonList(restTemplateInterceptor));
    restTemplate.getMessageConverters().add(enioJsonHttpMessageConverter);

    for (final HttpMessageConverter<?> messageConverter : restTemplate.getMessageConverters()) {
      if (messageConverter instanceof AllEncompassingFormHttpMessageConverter) {
        ((AllEncompassingFormHttpMessageConverter) messageConverter)
            .addPartConverter(enioJsonHttpMessageConverter);
        ((AllEncompassingFormHttpMessageConverter) messageConverter)
            .setCharset(Charset.forName("UTF-8"));
        ((AllEncompassingFormHttpMessageConverter) messageConverter)
            .setMultipartCharset(Charset.forName("UTF-8"));
      }
    }

    restTemplate.setErrorHandler(new EnioResponseErrorHandler());

    return restTemplate;
  }

  private CredentialsProvider provider(String id, String pwd) {
    CredentialsProvider provider = new BasicCredentialsProvider();
    UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(id, pwd);
    provider.setCredentials(AuthScope.ANY, credentials);
    return provider;
  }

  private static class HttpComponentsClientHttpRequestFactoryDigestAuth
      extends HttpComponentsClientHttpRequestFactory {

    public HttpComponentsClientHttpRequestFactoryDigestAuth(HttpClient httpClient) {
      super(httpClient);
    }

    @Override
    protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri) {
      HttpHost host = new HttpHost(uri.getHost(), uri.getPort(), uri.getScheme());

      AuthCache authCache = new BasicAuthCache();
      DigestScheme digestAuth = new DigestScheme();
      digestAuth.overrideParamter("realm", "enio service");
      authCache.put(host, digestAuth);

      BasicHttpContext localcontext = new BasicHttpContext();
      localcontext.setAttribute(HttpClientContext.AUTH_CACHE, authCache);
      return localcontext;
    }
  }
}
