package com.codescroll.notification.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Account {
  private long id;
  private boolean active;
  private String email;
  private String username;
}
