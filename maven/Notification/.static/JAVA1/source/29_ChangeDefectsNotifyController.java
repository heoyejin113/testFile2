package com.codescroll.notification.controller;

import com.codescroll.notification.dto.ChangeDefects;
import com.codescroll.notification.service.change.defects.ChangeDefectsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@CrossOrigin
@Slf4j
public class ChangeDefectsNotifyController {

  private final ChangeDefectsService changeDefectsService;

  @Autowired
  public ChangeDefectsNotifyController(ChangeDefectsService changeDefectsService) {
    this.changeDefectsService = changeDefectsService;
  }

  @RequestMapping(value = "changeDefects", method = RequestMethod.POST)
  public ResponseEntity<Void> changeDefects(@RequestBody List<ChangeDefects> changeDefectsDto) {
    log.info("post -> change defect");
    this.changeDefectsService.notifyChanges(changeDefectsDto);
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
