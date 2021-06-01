package com.codescroll.notification.service.message;

import com.codescroll.notification.dto.ProjectInfo;
import lombok.AllArgsConstructor;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.text.SimpleDateFormat;

@AllArgsConstructor
public class ChangeProjectStatusMessageGeneratorImpl implements IMessageGenerator {

  private ProjectInfo projectInfo;
  private String webUrl;
  private TemplateEngine templateEngine;

  @Override
  public String generateHtml() {
    Context context = new Context();
    context.setVariable("projectKeyAndName", projectInfo.getKey() + " / " + projectInfo.getName());
    context.setVariable("overviewPage", "http://" + webUrl + "/project/" + projectInfo.getKey() + "/overview");
    context.setVariable("projectInfo", projectInfo);
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(projectInfo.getRevision().getTimestamp());
    context.setVariable("date", date);
    context.setVariable("errorLog", "http://" + webUrl + "/project/" + projectInfo.getKey() + "/history");
    return templateEngine.process("changeProjectStatusTemplate.html", context);
  }
}
