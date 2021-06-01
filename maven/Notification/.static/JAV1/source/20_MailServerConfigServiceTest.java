package com.codescroll.notification.service;

import com.codescroll.notification.dto.MailServerConfigDTO;
import com.codescroll.notification.exception.MailServerException;
import com.codescroll.notification.fake.FakeMailServer;
import com.codescroll.notification.fake.FakeMailServerConfig;
import com.codescroll.notification.repository.MailServerConfig;
import com.codescroll.notification.repository.MailServerConfigRepository;
import com.codescroll.notification.service.config.MailServerConfigService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MailServerConfigServiceTest {
  @Autowired
  private MailServerConfigRepository mailServerConfigRepository;

  @Test
  public void setConfig() {

// GIVEN
    mailServerConfigRepository.deleteAll();
    MailServerConfigDTO mailServerConfigDTO = FakeMailServerConfig.MAIL_SERVER_CONFIG_DTO();
    MailServerConfig mailServerConfig = FakeMailServerConfig.MAIL_SERVER_CONFIG_DAO();
    if (mailServerConfigDTO == null || mailServerConfig == null) {
      fail("make condition fail");
    }

//WHEN
    MailServerConfigService mailServerConfigService = new MailServerConfigService(mailServerConfigRepository);
    mailServerConfigService.setConfig(mailServerConfigDTO);


//THEN
    Optional<MailServerConfig> mailServerConfigDAOOptional = mailServerConfigRepository.findById("mail");
    assertThat(mailServerConfigDAOOptional).isPresent();
    mailServerConfigDAOOptional.get().setChangePassword(true);
    assertThat(mailServerConfigDAOOptional.get()).isEqualToComparingFieldByField(mailServerConfig);
  }

  @Test
  public void updatePassword() {
// GIVEN
    mailServerConfigRepository.deleteAll();
    MailServerConfigDTO mailServerConfigDTO = FakeMailServerConfig.MAIL_SERVER_CONFIG_DTO();
    MailServerConfig mailServerConfig = FakeMailServerConfig.MAIL_SERVER_CONFIG_DAO();
    if (mailServerConfigDTO == null || mailServerConfig == null) {
      fail("make condition fail");
    }

    MailServerConfigService mailServerConfigService = new MailServerConfigService(mailServerConfigRepository);
    mailServerConfigService.setConfig(mailServerConfigDTO);


// WHEN changePassword false
    mailServerConfigDTO.setHost("test Host");
    mailServerConfigDTO.setPassword("changed password");
    mailServerConfigDTO.setChangePassword(false);
    mailServerConfigService.setConfig(mailServerConfigDTO);

// THEN
    Optional<MailServerConfig> mailServerConfigDAOOptional = mailServerConfigRepository.findById("mail");
    assertThat(mailServerConfigDAOOptional).isPresent();
    mailServerConfigDAOOptional.get().setChangePassword(true);
    mailServerConfig.setHost("test Host");
    assertThat(mailServerConfigDAOOptional.get()).isEqualToComparingFieldByField(mailServerConfig);

// WHEN changePassword true
    mailServerConfigDTO.setPassword("changed password");
    mailServerConfigDTO.setChangePassword(true);
    mailServerConfigService.setConfig(mailServerConfigDTO);

// THEN
    mailServerConfigDAOOptional = mailServerConfigRepository.findById("mail");
    assertThat(mailServerConfigDAOOptional).isPresent();
    mailServerConfigDAOOptional.get().setChangePassword(true);
    mailServerConfig.setPassword("changed password");
    assertThat(mailServerConfigDAOOptional.get()).isEqualToComparingFieldByField(mailServerConfig);
  }

  @Test
  public void getConfig() {

// GIVEN
    mailServerConfigRepository.deleteAll();
    MailServerConfigDTO fakeMailServerConfigDTO = FakeMailServerConfig.MAIL_SERVER_CONFIG_DTO();
    MailServerConfig fakeMailServerConfig = FakeMailServerConfig.MAIL_SERVER_CONFIG_DAO();

    if (fakeMailServerConfig == null || fakeMailServerConfigDTO == null) {
      fail("make condition fail");
    }

    mailServerConfigRepository.save(fakeMailServerConfig);

// WHEN
    MailServerConfigService mailServerConfigService = new MailServerConfigService(mailServerConfigRepository);
    Optional<MailServerConfigDTO> mailServerConfigDTOOptional = mailServerConfigService.getConfig();

// THEN
    assertThat(mailServerConfigDTOOptional).isPresent();

    // get 할때 changePassword 가 false 인지 검사
    fakeMailServerConfigDTO.setChangePassword(false);
    // get 할때 password 가 empty 인지 검사
    fakeMailServerConfigDTO.setPassword("");

    MailServerConfigDTO mailServerConfigDTO = mailServerConfigDTOOptional.get();
    assertThat(mailServerConfigDTO).isEqualToComparingFieldByField(fakeMailServerConfigDTO);
    assertThat(mailServerConfigDTO.getPassword()).isEmpty();
    assertThat(mailServerConfigDTO.isChangePassword()).isFalse();

  }

  @Test
  public void getConfigGivenNothing() {

// GIVEN
    // DB 에 아무 값도 없을 때
    mailServerConfigRepository.deleteAll();

// WHEN
    MailServerConfigService mailServerConfigService = new MailServerConfigService(mailServerConfigRepository);
    Optional<MailServerConfigDTO> mailServerConfigDTOOptional = mailServerConfigService.getConfig();

// THEN
    assertThat(mailServerConfigDTOOptional).isEmpty();
  }

  @Test
  public void emptyConfigConnectionTest() {
// GIVEN
    mailServerConfigRepository.deleteAll();
    MailServerConfigService mailServerConfigService = new MailServerConfigService(mailServerConfigRepository);
// WHEN
    try {
      mailServerConfigService.connectionTest(new MailServerConfigDTO());

// THEN
      fail("has must throw fail");
    } catch (MailServerException e) {
      assertThat(e.getMessage()).contains("Couldn't connect to host");
    }
  }

  @Test
  public void connectionFailTest() {
// GIVEN
    mailServerConfigRepository.deleteAll();
    MailServerConfigDTO fakeMailServerConfigDTO = FakeMailServerConfig.MAIL_SERVER_CONFIG_DTO();
    MailServerConfigService mailServerConfigService = new MailServerConfigService(mailServerConfigRepository);
    mailServerConfigService.setConfig(fakeMailServerConfigDTO);
// WHEN
    try {
      mailServerConfigService.connectionTest(fakeMailServerConfigDTO);

// THEN
      fail("has must throw fail");
    } catch (MailServerException e) {
      assertThat(e.getMessage()).contains("Couldn't connect to host");
    }
  }

  @Test
  public void connectionSuccessTest() {
    // GIVEN
    mailServerConfigRepository.deleteAll();
    MailServerConfigDTO fakeMailServerConfigDTO = FakeMailServerConfig.MAIL_SERVER_CONFIG_DTO();
    MailServerConfigService mailServerConfigService = new MailServerConfigService(mailServerConfigRepository);
    mailServerConfigService.setConfig(fakeMailServerConfigDTO);
    FakeMailServer fakeMailServer = new FakeMailServer();
    fakeMailServer.setHost(fakeMailServerConfigDTO.getHost());
    fakeMailServer.setPort(fakeMailServerConfigDTO.getPort());
// WHEN
    try {
      fakeMailServer.start();
      mailServerConfigService.connectionTest(fakeMailServerConfigDTO);
      fakeMailServer.stop();
// THEN
    } catch (MailServerException e) {
      fail(e.getMessage());
    }
  }
}
