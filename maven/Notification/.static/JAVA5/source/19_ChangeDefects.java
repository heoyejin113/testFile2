package com.codescroll.notification.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ChangeDefects {
  private List<DefectInfo> defects;
  private String assignTo;
  private Integer statusTo;
  private String updateComment;
  private long projectId;
}
