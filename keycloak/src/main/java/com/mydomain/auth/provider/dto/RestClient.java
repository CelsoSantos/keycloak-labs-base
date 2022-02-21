package com.mydomain.auth.provider.dto;

import com.mydomain.auth.provider.user.CustomUserStorageProvider;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@Consumes(MediaType.APPLICATION_JSON)
//@Produces(MediaType.APPLICATION_JSON)
public class RestClient {

  private static final Logger log = LoggerFactory.getLogger(CustomUserStorageProvider.class);

  @Setter
  @Getter
  private String amxToken;

//  public void addCircleRolesToUserRoles(String actionableContent) {
////	private void addCircleRolesToUserRoles(String actionableContent, JwtAdditionalContentDto jwtAdditionalContent) {
//
//    ObjectMapper mapper = new ObjectMapper();
//    CircleDto circle;
//
//    try {
//      circle = mapper.readValue(actionableContent, CircleDto.class);
//      List<String> roles = jwtAdditionalContent.getUserInfo().getUserRoles();
//      if (circle.isActionableContent()) {
//        roles.add("ac-consumer");
//      }
//      if(circle.isMipIndicative()) {
//        roles.add("mip-indicative");
//      }
//    } catch (JsonProcessingException e) {
//      log.error("[ERROR] addActionableContentToUserRoles", e);
//      e.printStackTrace();
//    }
//  }

  public LoginResponseDto mapLoginResponse(String loginResponse) {

    ObjectMapper mapper = new ObjectMapper();
    LoginResponseDto loginBodyResponse = new LoginResponseDto();

    try {
      loginBodyResponse = mapper.readValue(loginResponse, LoginResponseDto.class);
      return loginBodyResponse;
    } catch (JsonProcessingException e) {
      log.error("[ERROR] mapLoginResponse", e);
      e.printStackTrace();
    }

    return loginBodyResponse;
  }

  public String login(String email, String password) throws IOException {

    // String url = System.getenv("IDP_BASE_URL") + "/moik/ext/login/login";
    String url = System.getenv("IDP_BASE_URL") + "/v3/e93a0baa-1a46-4500-a117-4611d6c50139";
    // https://run.mocky.io/v3/e93a0baa-1a46-4500-a117-4611d6c50139

    HttpPost post = new HttpPost(url);
    // add request parameters
    List<NameValuePair> urlParameters = new ArrayList<>();
    urlParameters.add(new BasicNameValuePair("auth", "emailpassword"));
    urlParameters.add(new BasicNameValuePair("email", email));
    urlParameters.add(new BasicNameValuePair("password", password));

    post.setEntity(new UrlEncodedFormEntity(urlParameters));

    try (CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = httpClient.execute(post)) {

      return EntityUtils.toString(response.getEntity());
    }
  }

  public String getUserInfo(String amxToken) {

    // String currentUserPath = System.getenv("IDP_BASE_URL") + "/moik/ext/auth/current-user";
    String currentUserPath = System.getenv("IDP_BASE_URL") + "/v3/76075fe2-b722-4ac3-8ba8-bd07491c900f";
    // https://run.mocky.io/v3/76075fe2-b722-4ac3-8ba8-bd07491c900f

    HttpGet get = new HttpGet(currentUserPath);
    HttpContext localContext = getHttpContext(amxToken);
    String userInfo = "";

    try (CloseableHttpClient client = HttpClients.createDefault()) {
      CloseableHttpResponse response = client.execute(get, localContext);
      userInfo = EntityUtils.toString(response.getEntity());
      return userInfo;
    } catch (IOException e) {
      log.error("Error in getUserInfo: ", e);
    }

    return userInfo;
  }

  private HttpContext getHttpContext(String amxToken) {

    HttpContext localContext = new BasicHttpContext();
    localContext.setAttribute(HttpClientContext.COOKIE_STORE, setCookies(amxToken));

    return localContext;
  }

  private BasicCookieStore setCookies(String amxToken) {

    String domain = System.getenv("DOMAIN");

    BasicClientCookie cookie = new BasicClientCookie("tokenId", amxToken);
    cookie.setDomain(domain);
    cookie.setPath("/thirdparty");

    BasicCookieStore cookieStore = new BasicCookieStore();
    cookieStore.addCookie(cookie);

    return cookieStore;
  }

  public UserInfoDto mapUserInfo(String userInfo) {

    ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.registerModule(new Jdk8Module());
    UserInfoDto userInfoMapped = new UserInfoDto();

    try {
      userInfoMapped = mapper.readValue(userInfo, UserInfoDto.class);
      return userInfoMapped;
    } catch (JsonProcessingException e) {
      log.error("[ERROR] mapUserInfo", e);
      e.printStackTrace();
    }

    return userInfoMapped;
  }

}
