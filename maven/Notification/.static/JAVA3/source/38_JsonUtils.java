package com.codescroll.notification.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtils {
  public static <T> T jsonToObj(String json, Class<T> obj) throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.readValue(json, obj);
  }

  public static String objectToJson(Object obj) throws JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    return mapper.writeValueAsString(obj);
  }
}
