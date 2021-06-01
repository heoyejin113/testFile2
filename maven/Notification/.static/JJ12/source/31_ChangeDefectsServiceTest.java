package com.codescroll.notification.service;

import com.codescroll.defect.enums.DefectStatus;
import com.codescroll.notification.constants.DefectEvent;
import com.codescroll.notification.constants.TargetUser;
import com.codescroll.notification.dto.ChangeDefects;
import com.codescroll.notification.dto.DefectInfo;
import com.codescroll.notification.fake.FakeMailServer;
import com.codescroll.notification.fake.FakeMailServerConfig;
import com.codescroll.notification.repository.MailServerConfigRepository;
import com.codescroll.notification.rest.template.NotificationConfig;
import com.codescroll.notification.rest.template.ProjectServiceRestTemplate;
import com.codescroll.notification.service.change.defects.ChangeDefectsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ChangeDefectsServiceTest {


  @MockBean
  private MailServerConfigRepository mailServerConfigRepository;
  @MockBean
  private ProjectServiceRestTemplate projectServiceRestTemplate;

  @Autowired
  private ChangeDefectsService changeDefectsService;

  @Test
  public void notifyChange() {

    // GIVEN

    FakeMailServer fakeMailServer = new FakeMailServer();
    fakeMailServer.setHost("localhost");
    fakeMailServer.setPort(1234);

    given(mailServerConfigRepository.findById("mail"))
            .willReturn(Optional.of(FakeMailServerConfig.MAIL_SERVER_CONFIG_DAO()));

    List<Integer> event = new ArrayList<>();
    event.add(DefectEvent.REOPEN.ordinal());
    event.add(DefectEvent.ASSIGNED.ordinal());
    event.add(DefectEvent.SUPPRESSED.ordinal());
    event.add(DefectEvent.COMMENTED.ordinal());

    List<Integer> target = new ArrayList<>();
    target.add(TargetUser.DEFECT_ASSIGNEE.ordinal());


    given(projectServiceRestTemplate.getDefectNotificationConfig("DEVTEST"))
            .willReturn(Optional.of(new NotificationConfig(event, target)));
    given(projectServiceRestTemplate.getProjectInfo(1))
            .willReturn(Optional.of(FakeMailServerConfig.PROJECTINFO_DAO()));

    List<DefectInfo> defectInfos = generateDefectInfos();


    // WHEN
    //ChangeDefectsService changeDefectsService = new ChangeDefectsService(projectServiceRestTemplate);
    ChangeDefects changeDefects = ChangeDefects.builder().defects(defectInfos)
            // .statusTo(DefectStatus.DEFECT_STATUS_REOPEN.ordinal())
            .assignTo("malshan84@gmail.com")
            // .updateComment("test to test")
            .projectId(1).build();
    List<ChangeDefects> changeDefectsList = new ArrayList<>();
    changeDefectsList.add(changeDefects);


    //fakeMailServer.start();


    Future<Void> result = this.changeDefectsService.notifyChanges(changeDefectsList);
    try {
      result.get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }

    fakeMailServer.getMessage().forEach(wiserMessage -> {
      try {
        System.out.println(wiserMessage.getMimeMessage().getContent());
      } catch (IOException e) {
        e.printStackTrace();
      } catch (MessagingException e) {
        e.printStackTrace();
      }
    });
    //fakeMailServer.stop();
    // THEN
  }

  @Test
  public void notifyChangeTooMany() {

    // GIVEN

    FakeMailServer fakeMailServer = new FakeMailServer();
    fakeMailServer.setHost("localhost");
    fakeMailServer.setPort(1234);

    given(mailServerConfigRepository.findById("mail"))
            .willReturn(Optional.of(FakeMailServerConfig.MAIL_SERVER_CONFIG_DAO()));

    List<Integer> event = new ArrayList<>();
    event.add(DefectEvent.REOPEN.ordinal());
    event.add(DefectEvent.ASSIGNED.ordinal());
    event.add(DefectEvent.SUPPRESSED.ordinal());
    event.add(DefectEvent.COMMENTED.ordinal());

    List<Integer> target = new ArrayList<>();
    target.add(TargetUser.DEFECT_ASSIGNEE.ordinal());


    given(projectServiceRestTemplate.getDefectNotificationConfig("DEVTEST"))
            .willReturn(Optional.of(new NotificationConfig(event, target)));
    given(projectServiceRestTemplate.getProjectInfo(1))
            .willReturn(Optional.of(FakeMailServerConfig.PROJECTINFO_DAO()));

    List<DefectInfo> defectInfos = new ArrayList<>();


    for (int i = 0; i <= 50; i++) {
      defectInfos.addAll(generateDefectInfos());
    }


    // WHEN
    //ChangeDefectsService changeDefectsService = new ChangeDefectsService(projectServiceRestTemplate);
    ChangeDefects changeDefects = ChangeDefects.builder().defects(defectInfos)
            //.statusTo(DefectStatus.DEFECT_STATUS_REOPEN.ordinal())
            .assignTo("malshan84@gmail.com")
            .updateComment("test to test")
            .projectId(1).build();
    List<ChangeDefects> changeDefectsList = new ArrayList<>();
    changeDefectsList.add(changeDefects);


    //fakeMailServer.start();


    Future<Void> result = this.changeDefectsService.notifyChanges(changeDefectsList);
    try {
      result.get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }

    fakeMailServer.getMessage().forEach(wiserMessage -> {
      try {
        System.out.println(wiserMessage.getMimeMessage().getContent());
      } catch (IOException e) {
        e.printStackTrace();
      } catch (MessagingException e) {
        e.printStackTrace();
      }
    });
    //fakeMailServer.stop();
    // THEN
  }

  private List<DefectInfo> generateDefectInfos() {
    List<DefectInfo> defectInfos = new ArrayList<>();


    defectInfos.add(DefectInfo.builder()
            .status(DefectStatus.DEFECT_STATUS_OPEN.getValue())
            .assignee("")
            .rule("MISRA_TEST_0_1")
            .path("D:/JENKINS_ANAL/WORKSPACE/QA_PRM6_NUA_GIT_TEST_CG/DBUSCOMMON/INCLUDE/GLIB-2.0/GOBJECT/GTYPE.H")
            .message("A function with external linkage g_type_add_interface_dynamic has no definition")
            .id(1234)
            .build());

    defectInfos.add(DefectInfo.builder()
            .status(DefectStatus.DEFECT_STATUS_SUPPRESSED_ERROR.getValue())
            .assignee("malshan84@gma.com")
            .rule("MISRA_TEST_0_2")
            .path("D:/JENKINS_ANAL/WORKSPACE/INCLUDE/GLIB-2.0/GOBJECT/GTYPE.H")
            .message("A function with external linkage message")
            .id(1234)
            .build());

    defectInfos.add(DefectInfo.builder()
            .status(DefectStatus.DEFECT_STATUS_CLOSED.getValue())
            .assignee("malshan84@gma.com")
            .rule("MISRA_TEST_0_3")
            .path("D:/WORKSPACE/QA_PRM6_NUA_GIT_TEST_CG/DBUSCOMMON/INCLUDE/GLIB-2.0/GOBJECT/GTYPE.C")
            .message("linkage g_type_add_interface_dynamic has no definition")
            .id(1234)
            .functionSignature("test(int a)")
            .build());

    defectInfos.add(DefectInfo.builder()
            .status(DefectStatus.DEFECT_STATUS_RESOLVED.getValue())
            .assignee("malshan@suresofttech.com")
            .rule("MISRA_TEST_0_4")
            .path("D:GOBJECT/GTYPE.H")
            .message("function with external")
            .build());
    return defectInfos;
  }
}