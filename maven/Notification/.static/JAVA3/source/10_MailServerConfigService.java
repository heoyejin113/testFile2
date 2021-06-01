package com.codescroll.notification.service.config;

import com.codescroll.notification.dto.MailServerConfigDTO;
import com.codescroll.notification.exception.MailServerException;
import com.codescroll.notification.repository.MailServerConfig;
import com.codescroll.notification.repository.MailServerConfigRepository;
import com.codescroll.notification.sender.MailSenderWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.Optional;

@Service
@Slf4j
public class MailServerConfigService {

  private final MailServerConfigRepository mailServerConfigRepository;

  @Autowired
  public MailServerConfigService(MailServerConfigRepository mailServerConfigRepository) {
    this.mailServerConfigRepository = mailServerConfigRepository;
  }

  public Optional<MailServerConfigDTO> getConfig() {
    return this.mailServerConfigRepository.findById("mail")
            .map(config -> {
              MailServerConfigDTO dto = daoToDto(config);
              dto.setPassword("");
              dto.setChangePassword(false);
              return Optional.of(dto);
            })
            .orElse(Optional.empty());
  }

  public void setConfig(MailServerConfigDTO dto) {

    MailServerConfig mailServerConfig = dtoToDao(dto);

    if (!dto.isChangePassword()) {
      mailServerConfig.setPassword(getPasswordFromRepo());
    }

    this.mailServerConfigRepository.save(mailServerConfig);
  }

  private String getPasswordFromRepo() {
    return this.mailServerConfigRepository.findById("mail")
            .map(MailServerConfig::getPassword)
            .orElse(StringUtils.EMPTY);
  }


  public void connectionTest(MailServerConfigDTO dto) throws MailServerException {
    if (!dto.isChangePassword()) {
      dto.setPassword(
              this.mailServerConfigRepository.findById("mail")
                      .map(MailServerConfig::getPassword)
                      .orElse(StringUtils.EMPTY)
      );
    }

    MailSenderWrapper mailSenderWrapper = new MailSenderWrapper();
    mailSenderWrapper.initMailSender(dto);
    try {
      mailSenderWrapper.testConnection();
    } catch (MessagingException e) {
      log.info(e.getMessage());
      throw new MailServerException(e.getMessage(), e.getCause());
    } catch (Exception e) {
      log.error(e.getMessage(), e);
      throw new MailServerException(e.getMessage(), e.getCause());
    }
  }

  private MailServerConfigDTO daoToDto(MailServerConfig dao) {
    return new ModelMapper().map(dao, MailServerConfigDTO.class);
  }

  private MailServerConfig dtoToDao(MailServerConfigDTO dto) {
    return new ModelMapper().map(dto, MailServerConfig.class);
  }
}
