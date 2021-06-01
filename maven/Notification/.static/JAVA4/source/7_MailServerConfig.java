package com.codescroll.notification.repository;

import com.codescroll.notification.constants.MAIL_SERVER_PROTOCOL;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity
@Setter
@Getter
public class MailServerConfig {
  @Id
  private String key = "mail";

  private MAIL_SERVER_PROTOCOL protocol;

  @Column(columnDefinition = "CLOB")
  private String host;

  @Column(columnDefinition = "CLOB")
  private String userName;

  @Column(columnDefinition = "CLOB")
  private String fromAddress;

  @Column(columnDefinition = "CLOB")
  private String prefix;

  @Column(columnDefinition = "CLOB")
  private String password;

  private int port;

  private Long timeout;

  private boolean tls;

  @Transient
  private boolean changePassword;
}
