package com.codescroll.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class DefectInfo {

  private long id;
  private String rule;
  private String ruleSet;
  private List<RuleLabelDto> labels;
  private String message;
  private int startLine;
  private int endLine;
  private int startColumn;
  private int endColumn;
  private String path;
  private String sourceFileBodyHash;
  private String function;
  private String functionSignature;
  private ImageProperty severity;
  private ImageProperty confidence;
  private int repeat;
  private String status;
  private String assignee;
  private Account assigneeInfo;
  private int templateNumber;
  private boolean newlyFound;
  private String suppressionHash;

}
