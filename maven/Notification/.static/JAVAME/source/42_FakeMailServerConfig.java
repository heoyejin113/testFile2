package com.codescroll.notification.fake;

import com.codescroll.notification.dto.MailServerConfigDTO;
import com.codescroll.notification.dto.ProjectInfo;
import com.codescroll.notification.repository.MailServerConfig;
import com.codescroll.notification.utils.JsonUtils;

import java.io.IOException;

public class FakeMailServerConfig {

  public static String MAIL_SERVER_CONFIG_JSON() {
    return "{" +
            "\"protocol\":\"smtp\"," +
            "\"host\":\"smtp.gmail.com\"," +
            "\"port\":587," +
            "\"timeout\":100000," +
            "\"tls\":true," +
            "\"userName\":\"malshan84@gmail.com\"," +
            "\"password\":\"iic6501!\"," +
            "\"fromAddress\":\"noti@static.io\"," +
            "\"prefix\":\"STATIC\"," +
            "\"changePassword\":true" +
            "}";
  }

  public static String MAIL_SERVER_CONFIG_NULL_JSON() {
    return "{\n" +
            "  \"protocol\":null,\n" +
            "  \"host\":null,\n" +
            "  \"port\":null,\n" +
            "  \"timeout\":null,\n" +
            "  \"tls\":null,\n" +
            "  \"userName\":null,\n" +
            "  \"password\":null,\n" +
            "  \"fromAddress\":null,\n" +
            "  \"prefix\":null,\n" +
            "  \"changePassword\":null\n" +
            "}";
  }

  public static String PROJECT_INFO() {
    return "{\n" +
            "  \"projectId\": 0,\n" +
            "  \"name\": \"DEVTEST\",\n" +
            "  \"key\": \"DEVTEST\", \n" +
            "  \"projectStatus\": \"FINISHED\",\n" +
            "  \"owners\": []\n" +
            "}";
  }

  public static String FINISHED_PROJECT_INFO() {
    return "{\n" +
            "  \"projectId\": 101,\n" +
            "  \"name\": \"CWE Demo\",\n" +
            "  \"key\": \"CWED\",\n" +
            "  \"owners\": [\"malshan84@gmail.com\", \"admin@static.io\"],\n" +
            "  \"projectStatus\": \"FINISHED\",\n" +
            "  \"ruleSet\": [8],\n" +
            "  \"revision\": null,\n" +
            "  \"previousRevision\": null\n" +
            "}";
  }

  public static String ERROR_PROJECT_INFO() {
    return "{\n" +
            "  \"projectId\": 101,\n" +
            "  \"name\": \"CWE Demo\",\n" +
            "  \"key\": \"CWED\",\n" +
            "  \"owners\": [\"malshan84@gmail.com\", \"admin@static.io\"],\n" +
            "  \"projectStatus\": \"ERROR\",\n" +
            "  \"ruleSet\": [8],\n" +
            "  \"revision\": null,\n" +
            "  \"previousRevision\": null\n" +
            "}";
  }

  public static String FINISHED_PROJECT_AND_REVISION_INFO() {
    return "{\n" +
            "  \"projectId\": 101,\n" +
            "  \"name\": \"CWE Demo\",\n" +
            "  \"key\": \"CWED\",\n" +
            "  \"owners\": [\n" +
            "    \"malshan84@gmail.com\"\n" +
            "  ],\n" +
            "  \"projectStatus\": \"FINISHED\",\n" +
            "  \"ruleSet\": [\n" +
            "    8\n" +
            "  ],\n" +
            "  \"revision\": {\n" +
            "    \"revisionId\": \"4a68667e-6aa4-4daa-bc0d-64ffff7cdd4c\",\n" +
            "    \"sequence\": 5,\n" +
            "    \"timestamp\": 1557038388052,\n" +
            "    \"addedDefects\": 0,\n" +
            "    \"deletedDefects\": 0,\n" +
            "    \"suppressedDefects\": 0,\n" +
            "    \"totalDefects\": 3003,\n" +
            "    \"defectDensity\": 1,\n" +
            "    \"analysisStatus\": \"FINISHED\",\n" +
            "    \"analysisProgress\": 0\n" +
            "  },\n" +
            "  \"previousRevision\": {\n" +
            "    \"revisionId\": \"6533ff24-019b-438b-be35-6671d370823d\",\n" +
            "    \"sequence\": 4,\n" +
            "    \"timestamp\": 1557037796043,\n" +
            "    \"addedDefects\": 0,\n" +
            "    \"deletedDefects\": 0,\n" +
            "    \"suppressedDefects\": 0,\n" +
            "    \"totalDefects\": 3003,\n" +
            "    \"defectDensity\": 1,\n" +
            "    \"analysisStatus\": \"FINISHED\",\n" +
            "    \"analysisProgress\": 0\n" +
            "  }\n" +

            "}";
  }

  public static String ERROR_PROJECT_AND_REVISION_INFO() {
    return "{\n" +
            "  \"projectId\": 101,\n" +
            "  \"name\": \"CWE Demo\",\n" +
            "  \"key\": \"CWED\",\n" +
            "  \"owners\": [\n" +
            "    \"malshan84@gmail.com\"\n" +
            "  ],\n" +
            "  \"projectStatus\": \"ERROR\",\n" +
            "  \"ruleSet\": [\n" +
            "    8\n" +
            "  ],\n" +
            "  \"revision\": {\n" +
            "    \"revisionId\": \"6480bd86-9c82-4d12-b440-55427ad73e54\",\n" +
            "    \"sequence\": 10,\n" +
            "    \"timestamp\": 1557048297291,\n" +
            "    \"addedDefects\": 0,\n" +
            "    \"deletedDefects\": 0,\n" +
            "    \"suppressedDefects\": 0,\n" +
            "    \"totalDefects\": 3003,\n" +
            "    \"defectDensity\": 1,\n" +
            "    \"analysisStatus\": \"FINISHED\",\n" +
            "    \"analysisProgress\": 0\n" +
            "  },\n" +
            "  \"previousRevision\": {\n" +
            "    \"revisionId\": \"0f806d78-c767-4889-8269-a739e9f64d43\",\n" +
            "    \"sequence\": 9,\n" +
            "    \"timestamp\": 1557048185304,\n" +
            "    \"addedDefects\": 0,\n" +
            "    \"deletedDefects\": 0,\n" +
            "    \"suppressedDefects\": 0,\n" +
            "    \"totalDefects\": 3003,\n" +
            "    \"defectDensity\": 1,\n" +
            "    \"analysisStatus\": \"FINISHED\",\n" +
            "    \"analysisProgress\": 0\n" +
            "  }\n" +
            "}";
  }


  public static MailServerConfigDTO MAIL_SERVER_CONFIG_DTO() {
    try {
      return JsonUtils.jsonToObj(MAIL_SERVER_CONFIG_JSON(), MailServerConfigDTO.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static MailServerConfig MAIL_SERVER_CONFIG_DAO() {
    try {
      return JsonUtils.jsonToObj(MAIL_SERVER_CONFIG_JSON(), MailServerConfig.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static String MAIL_SERVER_CONFIG_GET() {
    try {
      MailServerConfigDTO dto = JsonUtils.jsonToObj(MAIL_SERVER_CONFIG_JSON(), MailServerConfigDTO.class);
      dto.setPassword("");
      dto.setChangePassword(false);
      return JsonUtils.objectToJson(dto);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "";
  }

  public static ProjectInfo PROJECTINFO_DAO() {
    try {
      return JsonUtils.jsonToObj(PROJECT_INFO(), ProjectInfo.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static ProjectInfo FINISHED_PROJECT_INFO_DAO() {
    try {
      return JsonUtils.jsonToObj(FINISHED_PROJECT_INFO(), ProjectInfo.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static ProjectInfo FINISHED_PROJECT_AND_REVISION_INFO_DAO() {
    try {
      return JsonUtils.jsonToObj(FINISHED_PROJECT_AND_REVISION_INFO(), ProjectInfo.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static ProjectInfo ERROR_PROJECT_INFO_DAO() {
    try {
      return JsonUtils.jsonToObj(ERROR_PROJECT_INFO(), ProjectInfo.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static ProjectInfo ERROR_PROJECT_AND_REVISION_INFO_DAO() {
    try {
      return JsonUtils.jsonToObj(ERROR_PROJECT_AND_REVISION_INFO(), ProjectInfo.class);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
