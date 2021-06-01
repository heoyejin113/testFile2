package com.codescroll.notification.constants;

public enum ProjectStatus {
  ALL("ALL"),
  PENDING("PENDING"),
  ANALYZING("ANALYZING"),
  FINISHED("FINISHED"),
  ERROR("ERROR"),
  CANCELED("CANCELED");

  private String value;

  public String getValue() {
    return this.value;
  }

  private ProjectStatus(String value) {
    this.value = value;
  }
}
