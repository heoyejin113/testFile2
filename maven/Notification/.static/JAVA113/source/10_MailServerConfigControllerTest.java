package com.codescroll.notification.controller;

import com.codescroll.notification.dto.MailServerConfigDTO;
import com.codescroll.notification.fake.FakeMailServer;
import com.codescroll.notification.fake.FakeMailServerConfig;
import com.codescroll.notification.repository.MailServerConfigRepository;
import com.codescroll.notification.utils.StringTestUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class MailServerConfigControllerTest {

  @MockBean
  MailServerConfigRepository mailServerConfigRepository;

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void putMailServerConfig() throws Exception {

    // WHEN:
    final ResultActions resultActions;
    {
      resultActions = mockMvc.perform(
              put("/config/mail")
                      .contentType(MediaType.APPLICATION_JSON_UTF8)
                      .content(FakeMailServerConfig.MAIL_SERVER_CONFIG_JSON()));
    }

    // THEN:
    {
      resultActions.andExpect(status().isOk());
    }
  }

  @Test
  public void getMailServerConfig() throws Exception {
    // GIVEN:
    {
      given(mailServerConfigRepository.findById("mail"))
              .willReturn(Optional.of(FakeMailServerConfig.MAIL_SERVER_CONFIG_DAO()));
    }

    // WHEN:
    final ResultActions resultActions;
    {
      resultActions = mockMvc.perform(
              get("/config/mail")
                      .contentType(MediaType.APPLICATION_JSON_UTF8));

    }

    // THEN:
    {
      resultActions.andExpect(status().isOk());
      MvcResult mvcResult = resultActions.andReturn();
      String contentAsString = mvcResult.getResponse().getContentAsString();

      assertThat(StringTestUtils.NORMALIZER(contentAsString)).isEqualTo(StringTestUtils.NORMALIZER(FakeMailServerConfig.MAIL_SERVER_CONFIG_GET()));
    }
  }

  @Test
  public void getMailServerConfigNotFound() throws Exception {
    // GIVEN:
    {
      given(mailServerConfigRepository.findById("mail")).willReturn(Optional.empty());
    }

    // WHEN:
    final ResultActions resultActions;
    {
      resultActions = mockMvc.perform(
              get("/config/mail")
                      .contentType(MediaType.APPLICATION_JSON_UTF8));

    }

    // THEN:
    {
      resultActions.andExpect(status().isNoContent());
    }
  }

  @Test
  public void testConnectionFail() throws Exception {
    // GIVEN
    given(mailServerConfigRepository.findById("mail")).willReturn(Optional.of(FakeMailServerConfig.MAIL_SERVER_CONFIG_DAO()));

    // WHEN
    ResultActions resultActions = mockMvc.perform(
            post("/config/mail/connection")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(FakeMailServerConfig.MAIL_SERVER_CONFIG_JSON())
    );

    //THEN
    resultActions.andExpect(status().isOk());

    JSONParser jsonParser = new JSONParser();
    JSONObject obj = (JSONObject) jsonParser.parse(resultActions.andReturn().getResponse().getContentAsString());
    System.out.println(obj.get("message"));
    assertThat(obj.get("result")).isEqualTo("FAIL");
  }

  @Test
  public void testConnectionEmptyJson() throws Exception {
    // GIVEN
    given(mailServerConfigRepository.findById("mail")).willReturn(Optional.of(FakeMailServerConfig.MAIL_SERVER_CONFIG_DAO()));

    // WHEN
    ResultActions resultActions = mockMvc.perform(
            post("/config/mail/connection")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(FakeMailServerConfig.MAIL_SERVER_CONFIG_NULL_JSON())
    );

    //THEN
    resultActions.andExpect(status().isOk());

    JSONParser jsonParser = new JSONParser();
    JSONObject obj = (JSONObject) jsonParser.parse(resultActions.andReturn().getResponse().getContentAsString());
    System.out.println(obj.get("message"));
    assertThat(obj.get("result")).isEqualTo("UNKNOWN");
  }

  @Test
  public void testConnectionSuccess() throws Exception {
    // GIVEN
    given(mailServerConfigRepository.findById("mail")).willReturn(Optional.of(FakeMailServerConfig.MAIL_SERVER_CONFIG_DAO()));

    MailServerConfigDTO mailServerConfigDTO = FakeMailServerConfig.MAIL_SERVER_CONFIG_DTO();
    FakeMailServer fakeMailServer = new FakeMailServer();
    fakeMailServer.setHost(mailServerConfigDTO.getHost());
    fakeMailServer.setPort(mailServerConfigDTO.getPort());
    fakeMailServer.start();


    // WHEN
    ResultActions resultActions = mockMvc.perform(
            post("/config/mail/connection")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .content(FakeMailServerConfig.MAIL_SERVER_CONFIG_JSON())
    );

    //THEN
    resultActions.andExpect(status().isOk());

    JSONParser jsonParser = new JSONParser();
    JSONObject obj = (JSONObject) jsonParser.parse(resultActions.andReturn().getResponse().getContentAsString());
    assertThat(obj.get("result")).isEqualTo("SUCCESS");
    System.out.println(obj.get("message"));

    fakeMailServer.stop();
  }
}
