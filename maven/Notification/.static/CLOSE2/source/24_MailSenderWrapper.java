package com.codescroll.notification.sender;


import com.codescroll.notification.constants.MAIL_SERVER_PROTOCOL;
import com.codescroll.notification.dto.MailServerConfigDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.util.Optional;
import java.util.Properties;

@Service
@Slf4j
public class MailSenderWrapper {

  private JavaMailSenderImpl javaMailSender;
  private MailServerConfigDTO mailServerConfigDTO;


  public void initMailSender(MailServerConfigDTO mailServerConfigDTO) {
    this.javaMailSender = new JavaMailSenderImpl();
    this.javaMailSender.setPort(mailServerConfigDTO.getPort());
    this.javaMailSender.setHost(mailServerConfigDTO.getHost());
    this.javaMailSender.setUsername(mailServerConfigDTO.getUserName());
    this.javaMailSender.setPassword(mailServerConfigDTO.getPassword());
    this.javaMailSender.setProtocol("smtp");

    Properties props = new Properties();
    // required for gmail
    props.put("mail.smtp.starttls.enable", mailServerConfigDTO.isTls());
    props.put("mail.smtp.auth", "true");
    if (mailServerConfigDTO.getProtocol() == MAIL_SERVER_PROTOCOL.secure_smtp) {
      props.put("mail.smtp.socketFactory.port", mailServerConfigDTO.getPort()); //SSL Port
      props.put("mail.smtp.socketFactory.class",
              "javax.net.ssl.SSLSocketFactory");
    }

    this.javaMailSender.setJavaMailProperties(props);

    this.mailServerConfigDTO = mailServerConfigDTO;
  }

  public void sendHtml(String message, String to, String subject) {
    MimeMessagePreparator messagePreparator = mimeMessage -> {
      MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
      InternetAddress from = new InternetAddress(this.mailServerConfigDTO.getFromAddress());
      from.setPersonal("STATIC notification");
      messageHelper.setFrom(from);
      messageHelper.setTo(to);
      messageHelper.setSubject(makePrefix() + subject);
      messageHelper.setText(message, true);
    };
    try {
      log.info("mail sending......");
      this.javaMailSender.send(messagePreparator);
      log.info("finish of send mail......");
    } catch (Exception e) {
      log.error(e.getMessage());
    }

  }

  private String makePrefix() {
    return Optional.ofNullable(this.mailServerConfigDTO.getPrefix()).map(str -> {
      if (str.isEmpty()) {
        return StringUtils.EMPTY;
      } else {
        return "[" + str + "] ";
      }
    }).orElse(StringUtils.EMPTY);
  }

  public void testConnection() throws MessagingException {
    this.javaMailSender.testConnection();

  }
}
