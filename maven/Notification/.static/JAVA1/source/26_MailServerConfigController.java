package com.codescroll.notification.controller;

import com.codescroll.notification.dto.MailConnectionResult;
import com.codescroll.notification.dto.MailServerConfigDTO;
import com.codescroll.notification.exception.MailServerException;
import com.codescroll.notification.service.config.MailServerConfigService;
import io.swagger.annotations.ApiOperation;
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
public class MailServerConfigController {

  private final MailServerConfigService mailServerConfigService;

  @Autowired
  public MailServerConfigController(MailServerConfigService mailServerConfigService) {
    this.mailServerConfigService = mailServerConfigService;
  }

  @ApiOperation(value = "update mail server config", httpMethod = "PUT", notes="메일 서버 설정을 업데이트, changePassword 옵션이 false면 password는 업데이트 하지 않는다.")
  @RequestMapping(value = "config/mail", method = RequestMethod.PUT)
  public ResponseEntity<Void> updateMailServerConfig(@RequestBody MailServerConfigDTO dto) {

    mailServerConfigService.setConfig(dto);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @RequestMapping(value = "config/mail", method = RequestMethod.GET)
  public ResponseEntity<MailServerConfigDTO> getConfig() {
    return mailServerConfigService.getConfig()
            .map(MailServerConfigController::okRequest)
            .orElse(noContentRequest());
  }

  private static ResponseEntity<MailServerConfigDTO> okRequest(MailServerConfigDTO config) {
    return new ResponseEntity<>(config, HttpStatus.OK);
  }

  private ResponseEntity<MailServerConfigDTO> noContentRequest() {
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @RequestMapping(value = "config/mail/connection", method = RequestMethod.POST)
  public ResponseEntity<MailConnectionResult> mailServerConnectTest(@RequestBody MailServerConfigDTO dto) {
    try {
      mailServerConfigService.connectionTest(dto);
    } catch (MailServerException e) {
      return new ResponseEntity<>(MailConnectionResult.Fail(e.getMessage()), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(MailConnectionResult.Unknown(e.getMessage()), HttpStatus.OK);
    }
    return new ResponseEntity<>(MailConnectionResult.Success(), HttpStatus.OK);
  }
}
