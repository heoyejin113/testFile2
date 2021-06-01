package com.codescroll.notification.service.change.project;

import com.codescroll.notification.config.Config;
import com.codescroll.notification.constants.ProjectStatus;
import com.codescroll.notification.constants.TargetUser;
import com.codescroll.notification.dto.Account;
import com.codescroll.notification.dto.ProjectInfo;
import com.codescroll.notification.rest.template.AccountServiceRestTemplate;
import com.codescroll.notification.rest.template.NotificationConfig;
import com.codescroll.notification.rest.template.ProjectServiceRestTemplate;
import com.codescroll.notification.service.message.ChangeProjectStatusMessageGeneratorImpl;
import com.codescroll.notification.service.notify.NotifyByEmail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;

@Service
@Slf4j
public class ChangeProjectStatusService {
  private final ProjectServiceRestTemplate projectServiceRestTemplate;
  private final NotifyByEmail notifyByEmail;
  private final Config config;
  private final TemplateEngine templateEngine;
  private final AccountServiceRestTemplate accountServiceRestTemplate;

  @Autowired
  public ChangeProjectStatusService(ProjectServiceRestTemplate projectServiceRestTemplate, NotifyByEmail notifyByEmail, Config config, TemplateEngine templateEngine, AccountServiceRestTemplate accountServiceRestTemplate) {
    this.projectServiceRestTemplate = projectServiceRestTemplate;
    this.notifyByEmail = notifyByEmail;
    this.config = config;
    this.templateEngine = templateEngine;
    this.accountServiceRestTemplate = accountServiceRestTemplate;
  }

  @Async
  public Future<Void> notifyChange(ProjectInfo projectInfo) {

    try {
      log.info("change project status notify start");

      Optional<NotificationConfig> projectNotificationConfig = projectServiceRestTemplate.getProjectNotificationConfig(projectInfo.getKey());

      Optional<List<Integer>> events = getEvents(projectNotificationConfig);

      if (!eventsHasStatus(events, projectInfo.getProjectStatus())) {
        log.info("this event is not target event : " + projectInfo.getProjectStatus());
        log.info("change project status notify finish");
        return null;
      }

      Optional<List<Integer>> eventTargets = getEventTargets(projectNotificationConfig);

      if (!eventTargets.isPresent()) {
        log.info("this project has not event target users!!");
        log.info("change project status notify finish");
        return null;
      }

      Optional<ProjectInfo> opProjectInfo = projectServiceRestTemplate.getProjectInfo(projectInfo.getProjectId());
      if (opProjectInfo.isPresent()) {
        projectInfo = opProjectInfo.get();
      }


      Map<String, String> targetUserEmail = new HashMap<>();

      List<Integer> targets = eventTargets.get();
      if (targets.contains(TargetUser.PROJECT_ADMINISTRATOR.ordinal())) {
        projectInfo.getOwners().forEach(owner -> {
          targetUserEmail.put(owner.getEmail(), owner.getEmail());
        });
      }
      if (targets.contains(TargetUser.USERS_WHO_STAR_PROJECT.ordinal())) {
        Optional<List<Account>> opFavoriteUsers = this.accountServiceRestTemplate.getFavoriteUsers(projectInfo.getKey());
        opFavoriteUsers.ifPresent(accounts -> accounts.forEach(account -> targetUserEmail.put(account.getEmail(), account.getEmail())));
      }

      final ProjectInfo tmpProjectInfo = projectInfo;
      targetUserEmail.forEach((key, email) -> {
        ChangeProjectStatusMessageGeneratorImpl changeProjectStatusMessageGenerator = new ChangeProjectStatusMessageGeneratorImpl(tmpProjectInfo, config.getWebUrl(), templateEngine);
        String subject = tmpProjectInfo.getName() + "(" + tmpProjectInfo.getKey() + ") project analysis result is " + tmpProjectInfo.getProjectStatus();
        notifyByEmail.sendMail(changeProjectStatusMessageGenerator, email, subject);
      });
    } catch (Exception e) {
      log.info("change project status notify ERROR!!!!!!!!!!!!");
      log.error(e.getMessage(), e);
    }
    log.info("change project status notify finish");
    return null;
  }

  private boolean eventsHasStatus(Optional<List<Integer>> events, String status) {
    if (!events.isPresent()) {
      return false;
    }

    ProjectStatus[] projectStatusArray = ProjectStatus.values();

    for (Integer event : events.get()) {
      if (projectStatusArray[event].getValue().equals(status)) {
        return true;
      }
    }

    return false;
  }

  private Optional<List<Integer>> getEventTargets(Optional<NotificationConfig> projectNotificationConfig) {
    if (!projectNotificationConfig.isPresent()) {
      return Optional.empty();
    }

    NotificationConfig notificationConfig = projectNotificationConfig.get();
    return Optional.ofNullable(notificationConfig.getTarget());

  }

  private Optional<List<Integer>> getEvents(Optional<NotificationConfig> projectNotificationConfig) {
    if (!projectNotificationConfig.isPresent()) {
      return Optional.empty();
    }

    NotificationConfig notificationConfig = projectNotificationConfig.get();
    return Optional.ofNullable(notificationConfig.getEvent());
  }
}
