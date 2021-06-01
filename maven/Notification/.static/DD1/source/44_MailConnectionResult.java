package com.codescroll.notification.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class MailConnectionResult {

  private Result result;
  private String Message;

  public static MailConnectionResult Success() {
    return new MailConnectionResult(Result.SUCCESS, "");
  }

  public static MailConnectionResult Fail(String message) {
    return new MailConnectionResult(Result.FAIL, message);
  }

  public static MailConnectionResult Unknown(String message) {
    return new MailConnectionResult(Result.UNKNOWN, message);
  }

  enum Result {
    SUCCESS,
    FAIL,
    UNKNOWN
  }
}


