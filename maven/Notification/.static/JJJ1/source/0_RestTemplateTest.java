package com.codescroll.notification.etc;


import com.codescroll.notification.config.Config;
import com.codescroll.notification.rest.template.ProjectServiceRestTemplate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Local 에서 Rest Template 테스트 하려면 @LoadBalanced 옵션을 꺼야함
 */
@RunWith(SpringRunner.class)
@SpringBootTest
//@EnableCircuitBreaker
//@EnableEurekaClient
//@RefreshScope
@Configuration
public class RestTemplateTest {

  @Autowired
  Config config;

  @Autowired
  ProjectServiceRestTemplate projectServiceRestTemplate;

  @Test
  public void getNotificationConf() {
    // projectServiceRestTemplate.getProjectNotificationConfig("BBB");
    projectServiceRestTemplate.getDefectNotificationConfig("BBB");
  }

  @Test
  public void getProjectInfo() {
    projectServiceRestTemplate.getProjectInfo(34);
  }
}
