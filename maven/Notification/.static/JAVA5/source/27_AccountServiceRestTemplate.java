package com.codescroll.notification.rest.template;

import com.codescroll.notification.dto.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.springframework.web.util.UriComponentsBuilder.fromUri;

@Component
public class AccountServiceRestTemplate {

  private final RestTemplate restTemplate;

  // private final String PROJECT_SERVICE_ID = "211.116.222.92:19110";
  private final String PROJECT_SERVICE_ID = "auth-service";

  @Autowired
  public AccountServiceRestTemplate(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public Optional<List<Account>> getFavoriteUsers(String projectKey) {
    URI uri = URI.create("http://" + PROJECT_SERVICE_ID + "/account/users/favoriteProjectKey");
    Account[] accounts = this.restTemplate.getForObject(
            fromUri(uri).queryParam("key", projectKey).build().toString(), Account[].class
    );

    List<Account> accountList = Arrays.asList(accounts);

    return Optional.ofNullable(accountList);
  }

}
