package com.codescroll.notification.controller;

import com.codescroll.notification.rest.template.ProjectServiceRestTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ChangeDefectsNotifyControllerTest {

  @MockBean
  ProjectServiceRestTemplate projectServiceRestTemplate;
  @Autowired
  private MockMvc mockMvc;

  @Test
  public void notifyChangeDefectsTest() throws Exception {
    // GIVEN =========================
//    List<DefectInfo> defectInfos = new ArrayList<>();
//    defectInfos.add(DefectInfo.builder().projectId(1).status(DefectEvent.ASSIGNED.ordinal()).assignee("malshan@test.com").build());
//    defectInfos.add(DefectInfo.builder().projectId(1).status(DefectEvent.SUPPRESSED.ordinal()).assignee("malshan@test.com").build());
//    defectInfos.add(DefectInfo.builder().projectId(1).status(DefectEvent.REOPEN.ordinal()).assignee("kk@test.com").build());
//    defectInfos.add(DefectInfo.builder().projectId(1).status(DefectEvent.REOPEN.ordinal()).assignee("pp@test.com").build());
//
//    ChangeDefects changeDefects = ChangeDefects.builder()
//            .defects(defectInfos)
//            .statusTo(DefectEvent.REOPEN.ordinal())
//            .assignTo("haha@naver.com")
//            .build();
//
//    List<Integer> event = new ArrayList<>();
//    event.add(DefectEvent.REOPEN.ordinal());
//
//    List<Integer> target = new ArrayList<>();
//    target.add(TargetUser.DEFECT_ASSIGNEE.ordinal());
//
//    given(projectServiceRestTemplate.getDefectNotificationConfig("P"))
//            .willReturn(Optional.of(new NotificationConfig(event, target)));
//
//    ObjectMapper mapper = new ObjectMapper();
//    String json = mapper.writeValueAsString(changeDefects);

    // WHEN ============================
//    mockMvc.perform(
//            post("/changeDefects").contentType(MediaType.APPLICATION_JSON).content(json))
//            .andExpect(status().isOk());

  }
	System.out.println("hello java!");
}
