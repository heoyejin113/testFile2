package com.codescroll.notification;

import com.codescroll.commons.ServiceHome;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.MalformedURLException;


@SpringBootApplication
@EnableCircuitBreaker
@EnableEurekaClient
@RefreshScope
@Configuration
@Slf4j
@EnableAsync
public class NotificationApplication {
  public static void main(String[] args) {

    ServiceHome home = ServiceHome.getInstance();

    home.initHome("notification");
    try {
      String uri = home.initDB(new String[]{"AUTO_SERVER=TRUE"});
    } catch (MalformedURLException e) {
      log.error("Unable set db file path: " + e);
      return; // abort...
    }

    SpringApplication.run(NotificationApplication.class, args);
  }


}
