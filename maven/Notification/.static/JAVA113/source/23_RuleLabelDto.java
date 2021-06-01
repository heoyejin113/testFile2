package com.codescroll.notification.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RuleLabelDto {
  private long id;
  private String name;
  private String url;
}
