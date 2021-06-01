package com.codescroll.notification.rest.template;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
public class NotificationConfig {
  private List<Integer> event;
  private List<Integer> target;
}
