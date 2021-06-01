package com.codescroll.notification.rest.template;

import com.codescroll.notification.dto.ProjectInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Component
@Slf4j
public class ProjectServiceRestTemplate {

  private final RestTemplate restTemplate;
  //private final String PROJECT_SERVICE_ID = "211.116.222.92:18060";
  private final String PROJECT_SERVICE_ID = "project-service";

  @Autowired
  public ProjectServiceRestTemplate(RestTemplate restTemplate) {
    this.restTemplate = restTemplate;
  }

  public Optional<NotificationConfig> getDefectNotificationConfig(String projectKey) {
    return this.getNotificationConfig(projectKey, "notification_defect");
  }

  public Optional<NotificationConfig> getProjectNotificationConfig(String projectKey) {
    return this.getNotificationConfig(projectKey, "notification_project");
  }

  private Optional<NotificationConfig> getNotificationConfig(String projectKey, String notificationSettingKey) {
    URI uri = URI.create(String.format("http://" + PROJECT_SERVICE_ID + "/projects/key/%s/settings", projectKey));
    NotificationConfig forObject = this.restTemplate.getForObject(
            UriComponentsBuilder.fromUri(uri).queryParam("settingKey", notificationSettingKey).build().toString(),
            NotificationConfig.class
    );
    return Optional.ofNullable(forObject);
  }

  public Optional<ProjectInfo> getProjectInfo(long id) {
    URI uri = URI.create(String.format("http://" + PROJECT_SERVICE_ID + "/projects/id/%d", id));
    ProjectInfo forObject = this.restTemplate.getForObject(
            UriComponentsBuilder.fromUri(uri).build().toString(),
            ProjectInfo.class
    );
    return Optional.ofNullable(forObject);
  }


}
