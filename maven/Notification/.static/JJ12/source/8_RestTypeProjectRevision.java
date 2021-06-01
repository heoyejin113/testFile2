package com.codescroll.notification.dto;

import lombok.Data;

@Data
public class RestTypeProjectRevision {
  private String revisionId;
  private int sequence;
  private long timestamp;
  private int addedDefects;
  private int deletedDefects;
  private int suppressedDefects;
  private int totalDefects;
  private float defectDensity;
  private String analysisStatus;
  private int analysisProgress;
}
