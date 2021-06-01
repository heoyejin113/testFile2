package com.codescroll.notification.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ImageProperty {
  private String description;
  private int kind;
}
