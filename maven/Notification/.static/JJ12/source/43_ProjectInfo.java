package com.codescroll.notification.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProjectInfo {
  private Long projectId;
  private String name;
  private String key;
  private List<Account> owners;
  private String projectStatus;
  private List<Integer> ruleSet;
  private RestTypeProjectRevision revision;
  private RestTypeProjectRevision previousRevision;
}
