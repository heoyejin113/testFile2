package com.codescroll.notification.utils;

public class StringTestUtils {
  public static String NORMALIZER(String json) {
    return json.replaceAll("\n", "").replaceAll("\\s", "");
  }
}
