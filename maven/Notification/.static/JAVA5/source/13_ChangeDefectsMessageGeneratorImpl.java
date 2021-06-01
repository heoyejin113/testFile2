package com.codescroll.notification.service.message;

import com.codescroll.defect.enums.DefectStatus;
import com.codescroll.notification.dto.ChangeDefects;
import com.codescroll.notification.dto.DefectInfo;
import com.codescroll.notification.dto.ProjectInfo;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ChangeDefectsMessageGeneratorImpl implements IMessageGenerator {

  private List<DefectInfo> defectInfos;
  private String assignTo;
  private Integer statusTo;
  private TemplateEngine templateEngine;
  private ProjectInfo projectInfo;
  private String webUrl;
  private String updateComment;

  public ChangeDefectsMessageGeneratorImpl(List<DefectInfo> defectInfos, ChangeDefects changeDefects, TemplateEngine templateEngine, ProjectInfo projectInfo, String webUrl) {
    this.defectInfos = defectInfos;
    this.assignTo = changeDefects.getAssignTo();
    this.statusTo = changeDefects.getStatusTo();
    this.templateEngine = templateEngine;
    this.projectInfo = projectInfo;
    this.webUrl = webUrl;
    this.updateComment = changeDefects.getUpdateComment();
  }

  public ChangeDefectsMessageGeneratorImpl(ChangeDefects changeDefects, TemplateEngine templateEngine, ProjectInfo projectInfo, String webUrl) {
    this.defectInfos = changeDefects.getDefects();
    this.assignTo = changeDefects.getAssignTo();
    this.statusTo = changeDefects.getStatusTo();
    this.templateEngine = templateEngine;
    this.projectInfo = projectInfo;
    this.webUrl = webUrl;
    this.updateComment = changeDefects.getUpdateComment();
  }

  @Override
  public String generateHtml() {


    Context context = new Context();
    context.setVariable("projectKeyAndName", projectInfo.getKey() + " / " + projectInfo.getName());
    context.setVariable("defectInfos", defectInfos);

    List<String> tooManyDefectsMessages = new ArrayList<>();
    Optional.ofNullable(updateComment).ifPresent(comment -> {
      context.setVariable("updateComment", comment);
      tooManyDefectsMessages.add(String.format("Comment has been update for %d defects.", defectInfos.size()));
    });

    Optional.ofNullable(assignTo).ifPresent(assignee -> {
      context.setVariable("assignTo", assignee);
      tooManyDefectsMessages.add(String.format("%d defects have been assigned.", defectInfos.size()));
    });

    Optional.ofNullable(statusTo).ifPresent(status -> {
      if (status != 10) {
        context.setVariable("statusTo", DefectStatus.NumberToString(status));
        tooManyDefectsMessages.add(String.format("%d defects have been changed to \"%s\" status.", defectInfos.size(), DefectStatus.NumberToString(statusTo)));
      }
    });

    context.setVariable("overviewPage", "http://" + webUrl + "/project/" + projectInfo.getKey() + "/overview");
    context.setVariable("defectPage", "http://" + webUrl + "/project/" + projectInfo.getKey() + "/defect-list/");

    if (defectInfos.size() >= 20) {
      context.setVariable("tooManyDefectsMessages", tooManyDefectsMessages);
      return templateEngine.process("tooManyDefects.html", context);
    }

    return templateEngine.process("changeDefects.html", context);
  }
}
