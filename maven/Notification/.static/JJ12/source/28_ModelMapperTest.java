package com.codescroll.notification.etc;

import com.codescroll.notification.dto.MailServerConfigDTO;
import com.codescroll.notification.fake.FakeMailServerConfig;
import com.codescroll.notification.repository.MailServerConfig;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import java.text.SimpleDateFormat;
import java.util.Optional;

public class ModelMapperTest {
  @Test
  public void test() {
    MailServerConfigDTO dto = FakeMailServerConfig.MAIL_SERVER_CONFIG_DTO();
    MailServerConfig dao = FakeMailServerConfig.MAIL_SERVER_CONFIG_DAO();
    dao.setPassword("kk");
    dto.setChangePassword(false);
    ModelMapper modelMapper = new ModelMapper();
    dao = modelMapper.map(dto, MailServerConfig.class);
    System.out.println(dao.getPassword());
  }

  @Test
  public void test1() {

    Optional<MailServerConfigDTO> empty = Optional.empty();
    System.out.println(empty.map(MailServerConfigDTO::getPassword).orElse("empty!!"));
    //.ifPresent(dto -> System.out.println("empty!!"));

    MailServerConfigDTO mailServerConfigDTO = new MailServerConfigDTO();
    mailServerConfigDTO.setPassword("password");

    Optional<MailServerConfigDTO> notempty = Optional.ofNullable(mailServerConfigDTO);
    System.out.println(notempty.map(MailServerConfigDTO::getPassword).orElse("empty!!"));

  }

  @Test
  public void date() {
    long ldate = 1557038388052L;
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(ldate);
    System.out.println(date);

  }
}
