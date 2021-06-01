package com.codescroll.notification.controller;

import com.codescroll.notification.dto.ProjectInfo;
import com.codescroll.notification.service.change.project.ChangeProjectStatusService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@CrossOrigin
@Slf4j
public class ChangeProjectStatusNotifyController {

  private final ChangeProjectStatusService changeProjectStatusService;

  @Autowired
  public ChangeProjectStatusNotifyController(ChangeProjectStatusService changeProjectStatusService) {
    this.changeProjectStatusService = changeProjectStatusService;
  }

  @RequestMapping(value = "changeProjectStatus", method = RequestMethod.POST)
  public ResponseEntity<Void> changeProjectStatus(@RequestBody ProjectInfo projectInfo) {
    log.info("post -> change project info");
    changeProjectStatusService.notifyChange(projectInfo);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
