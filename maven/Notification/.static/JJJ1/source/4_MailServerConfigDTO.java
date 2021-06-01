package com.codescroll.notification.dto;

import com.codescroll.notification.constants.MAIL_SERVER_PROTOCOL;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailServerConfigDTO {
  private MAIL_SERVER_PROTOCOL protocol = MAIL_SERVER_PROTOCOL.smtp;
  private String host;
  private int port;
  private Long timeout;
  private boolean tls;
  private String userName;
  private String password;
  private String fromAddress;
  private String prefix;
  private boolean changePassword;
}

