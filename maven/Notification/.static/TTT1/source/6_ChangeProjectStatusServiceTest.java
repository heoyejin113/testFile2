package com.codescroll.notification.service;

import com.codescroll.notification.constants.ProjectStatus;
import com.codescroll.notification.constants.TargetUser;
import com.codescroll.notification.fake.FakeMailServerConfig;
import com.codescroll.notification.repository.MailServerConfigRepository;
import com.codescroll.notification.rest.template.NotificationConfig;
import com.codescroll.notification.rest.template.ProjectServiceRestTemplate;
import com.codescroll.notification.service.change.project.ChangeProjectStatusService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ChangeProjectStatusServiceTest {

  @MockBean
  private MailServerConfigRepository mailServerConfigRepository;

  @Autowired
  private ChangeProjectStatusService changeProjectStatusService;

  @MockBean
  private ProjectServiceRestTemplate projectServiceRestTemplate;

//  @MockBean
//  private AccountServiceRestTemplate accountServiceRestTemplate;

  @Test
  public void notifyChange() {


    given(mailServerConfigRepository.findById("mail"))
            .willReturn(Optional.of(FakeMailServerConfig.MAIL_SERVER_CONFIG_DAO()));

    given(projectServiceRestTemplate.getProjectInfo(101))
            .willReturn(Optional.ofNullable(FakeMailServerConfig.FINISHED_PROJECT_AND_REVISION_INFO_DAO()));

    List<Integer> event = new ArrayList<>();
    event.add(ProjectStatus.FINISHED.ordinal());
    event.add(ProjectStatus.ERROR.ordinal());


    List<Integer> target = new ArrayList<>();
    target.add(TargetUser.PROJECT_ADMINISTRATOR.ordinal());
    target.add(TargetUser.USERS_WHO_STAR_PROJECT.ordinal());

    given(projectServiceRestTemplate.getProjectNotificationConfig("CWED"))
            .willReturn(Optional.of(new NotificationConfig(event, target)));

//    List<Account> favoriteUser = new ArrayList<>();
//
//    favoriteUser.add(Account.builder().email("malshan@suresofttech.com").build());
//    favoriteUser.add(Account.builder().email("malshan84@gmail.com").build());
//    favoriteUser.add(Account.builder().email("malshan@naver.com").build());
//    given(accountServiceRestTemplate.getFavoriteUsers("CWED"))
//            .willReturn(Optional.ofNullable(favoriteUser));

    Future<Void> result = changeProjectStatusService.notifyChange(FakeMailServerConfig.FINISHED_PROJECT_INFO_DAO());

    try {
      result.get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void notifyChangeOnError() {


    given(mailServerConfigRepository.findById("mail"))
            .willReturn(Optional.of(FakeMailServerConfig.MAIL_SERVER_CONFIG_DAO()));

    given(projectServiceRestTemplate.getProjectInfo(101))
            .willReturn(Optional.ofNullable(FakeMailServerConfig.ERROR_PROJECT_AND_REVISION_INFO_DAO()));

    List<Integer> event = new ArrayList<>();
    event.add(ProjectStatus.FINISHED.ordinal());
    event.add(ProjectStatus.ERROR.ordinal());


    List<Integer> target = new ArrayList<>();
    target.add(TargetUser.PROJECT_ADMINISTRATOR.ordinal());

    given(projectServiceRestTemplate.getProjectNotificationConfig("CWED"))
            .willReturn(Optional.of(new NotificationConfig(event, target)));

    Future<Void> result = changeProjectStatusService.notifyChange(FakeMailServerConfig.ERROR_PROJECT_INFO_DAO());
    try {
      result.get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
  }

}
