package com.codescroll.notification.service.notify;

import com.codescroll.notification.dto.MailServerConfigDTO;
import com.codescroll.notification.repository.MailServerConfig;
import com.codescroll.notification.repository.MailServerConfigRepository;
import com.codescroll.notification.sender.MailSenderWrapper;
import com.codescroll.notification.service.message.IMessageGenerator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class NotifyByEmail {
  @Autowired
  private MailSenderWrapper mailSenderWrapper;

  @Autowired
  private MailServerConfigRepository mailServerConfigRepository;

  public void sendMail(IMessageGenerator messageGenerator, String to, String subject) {
    Optional<MailServerConfig> mailServerConfig = mailServerConfigRepository.findById("mail");
    mailServerConfig.ifPresent(config -> {
      mailSenderWrapper.initMailSender(daoToDto(config));
      mailSenderWrapper.sendHtml(messageGenerator.generateHtml(), to, subject);
      return;
    });
  }

  private MailServerConfigDTO daoToDto(MailServerConfig dao) {
    return new ModelMapper().map(dao, MailServerConfigDTO.class);
  }
}
