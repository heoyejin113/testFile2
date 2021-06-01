package com.codescroll.notification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ResponseMessage;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
  @Bean
  public Docket api() {

    List<ResponseMessage> msg = new ArrayList<>();
    msg.add(new ResponseMessageBuilder().code(500).message("INTERNAL SERVER ERROR").responseModel(new ModelRef("Error")).build());
    msg.add(new ResponseMessageBuilder().code(400).message("BAD REQUEST").build());
    msg.add(new ResponseMessageBuilder().code(404).message("NOT FOUND").build());
    msg.add(new ResponseMessageBuilder().code(409).message("CONFLICT").build());
    msg.add(new ResponseMessageBuilder().code(200).message("OK").build());
    msg.add(new ResponseMessageBuilder().code(204).message("NO_CONTENT").build());
    msg.add(new ResponseMessageBuilder().code(201).message("CREATED").build());

    return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .select()
            .apis(RequestHandlerSelectors.basePackage("com.codescroll.notification.controller"))
            .paths(PathSelectors.ant("/**"))
            .build()
            .globalResponseMessage(RequestMethod.GET, msg);
  }

  private ApiInfo apiInfo() {
    return new ApiInfoBuilder()
            .title("STATIC Doc.")
            .description("Notification Service API Document.")
            .build();

  }
}
