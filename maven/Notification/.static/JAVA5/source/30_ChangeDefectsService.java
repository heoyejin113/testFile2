package com.codescroll.notification.service.change.defects;

import com.codescroll.defect.enums.DefectStatus;
import com.codescroll.notification.config.Config;
import com.codescroll.notification.constants.DefectEvent;
import com.codescroll.notification.dto.ChangeDefects;
import com.codescroll.notification.dto.DefectInfo;
import com.codescroll.notification.dto.ProjectInfo;
import com.codescroll.notification.rest.template.NotificationConfig;
import com.codescroll.notification.rest.template.ProjectServiceRestTemplate;
import com.codescroll.notification.service.message.ChangeDefectsMessageGeneratorImpl;
import com.codescroll.notification.service.notify.NotifyByEmail;
import io.reactivex.Observable;
import io.reactivex.observables.GroupedObservable;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Future;

@Service
@Slf4j
public class ChangeDefectsService {

  private final ProjectServiceRestTemplate projectServiceRestTemplate;
  private final NotifyByEmail notifyByEmail;
  private final TemplateEngine templateEngine;
  private final Config config;

  @Autowired
  public ChangeDefectsService(ProjectServiceRestTemplate projectServiceRestTemplate, NotifyByEmail notifyByEmail, TemplateEngine templateEngine, Config config) {
    this.projectServiceRestTemplate = projectServiceRestTemplate;
    this.notifyByEmail = notifyByEmail;
    this.templateEngine = templateEngine;
    this.config = config;
  }

  @Async
  public Future<Void> notifyChanges(List<ChangeDefects> changeDefectsList) {

    try {
      log.info("change defects notify start");
      this.getProjectInfo(changeDefectsList)
              .ifPresent(
                      projectInfo -> projectServiceRestTemplate.getDefectNotificationConfig(projectInfo.getKey())
                              .ifPresent(
                                      notificationConfig -> Observable.fromIterable(changeDefectsList)
                                              .filter(changeDefects -> hasTargetEvent(changeDefects, notificationConfig))
                                              .subscribe(defects -> notifyChangeEachEvent(defects, projectInfo)
                                              )
                              )
              );
    } catch (Exception e) {
      e.printStackTrace();
      log.error(e.getMessage());
      log.info("change defects notify ERROR!!!!!!!!!!!!!!!");
      return null;
    }
    log.info("change defects notify finish");
    return null;
  }

  private Optional<ProjectInfo> getProjectInfo(List<ChangeDefects> changeDefectsList) {
    long id = this.getProjectId(changeDefectsList);
    if (id == -1) {
      return Optional.empty();
    }

    return projectServiceRestTemplate.getProjectInfo(id);
  }

  private long getProjectId(List<ChangeDefects> changeDefectsList) {
    return Optional.ofNullable(changeDefectsList).map(changeDefects -> {
      if (changeDefects.isEmpty()) {
        return -1L;
      }
      return changeDefects.get(0).getProjectId();
    }).orElse(-1L);
  }


  private void notifyChangeEachEvent(ChangeDefects changeDefects, ProjectInfo projectInfo) {

    if (Optional.ofNullable(changeDefects.getAssignTo()).isPresent()) {
      ChangeDefectsMessageGeneratorImpl changeDefectsMessageGenerator = new ChangeDefectsMessageGeneratorImpl(changeDefects, templateEngine, projectInfo, config.getWebUrl());
      notifyByEmail.sendMail(changeDefectsMessageGenerator, changeDefects.getAssignTo(), "change event notification mail!!");
      return;
    }

    Observable<DefectInfo> defectInfoObservable = Observable.fromIterable(changeDefects.getDefects());

    defectInfoObservable
            .groupBy(DefectInfo::getAssignee)
            .subscribe(defectInfoGroup -> notifyChangeEachUser(defectInfoGroup, projectInfo, changeDefects));
  }

  private boolean hasTargetEvent(ChangeDefects changeDefects, NotificationConfig notificationConfig) {
    // assingee 가 변경되고 notification 설정에 assignee변경 알람이 있는 경우 true
    if (Optional.ofNullable(changeDefects.getAssignTo()).isPresent()) {
      if (notificationConfig.getEvent().contains(DefectEvent.ASSIGNED.ordinal())) {
        return true;
      }
    }

    if (Optional.ofNullable(changeDefects.getUpdateComment()).isPresent()) {
      if (notificationConfig.getEvent().contains(DefectEvent.COMMENTED.ordinal())) {
        if (!changeDefects.getUpdateComment().isEmpty()) {
          return true;
        }
      }
    }

    DefectStatus[] values = DefectStatus.values();

    if (!Optional.ofNullable(changeDefects.getStatusTo()).isPresent()) {
      return false;
    }

    if (values.length <= changeDefects.getStatusTo()) {
      log.warn("defect has wrong status (" + changeDefects.getStatusTo() + ")");
      return false;
    }

    DefectStatus status = values[changeDefects.getStatusTo()];
    boolean hasTargetEvent;
    switch (status) {
      case DEFECT_STATUS_SELECTED:
        hasTargetEvent = notificationConfig.getEvent().contains(DefectEvent.SELECTED.ordinal());
        break;
      case DEFECT_STATUS_SUPPRESSED_BASELINE:
      case DEFECT_STATUS_SUPPRESSED_DUPLICATE:
      case DEFECT_STATUS_SUPPRESSED_ERROR:
      case DEFECT_STATUS_SUPPRESSED_FALSE_ALARM:
      case DEFECT_STATUS_SUPPRESSED_INTENDED:
        hasTargetEvent = notificationConfig.getEvent().contains(DefectEvent.SUPPRESSED.ordinal());
        break;
      case DEFECT_STATUS_RESOLVED:
        hasTargetEvent = notificationConfig.getEvent().contains(DefectEvent.RESOLVED.ordinal());
        break;
      case DEFECT_STATUS_REOPEN:
        hasTargetEvent = notificationConfig.getEvent().contains(DefectEvent.REOPEN.ordinal());
        break;
      default:
        hasTargetEvent = false;
        break;
    }

    return hasTargetEvent;
  }

  private void notifyChangeEachUser(GroupedObservable<String, DefectInfo> defectInfoGroup, ProjectInfo projectInfo, ChangeDefects changeDefects) {
    defectInfoGroup
            .reduce(new ArrayList<DefectInfo>(), (list, defectInfo) -> {
              list.add(defectInfo);
              return list;
            })
            .subscribe(
                    defectInfos -> {
                      ChangeDefectsMessageGeneratorImpl changeDefectsMessageGenerator = new ChangeDefectsMessageGeneratorImpl(defectInfos, changeDefects, templateEngine, projectInfo, config.getWebUrl());
                      notifyByEmail.sendMail(changeDefectsMessageGenerator, defectInfoGroup.getKey(), "change event notification mail!!");
                    }
            );
  }
}
