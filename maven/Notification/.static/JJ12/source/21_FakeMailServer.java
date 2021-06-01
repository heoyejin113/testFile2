package com.codescroll.notification.fake;

import org.subethamail.wiser.Wiser;
import org.subethamail.wiser.WiserMessage;

import java.util.List;

public class FakeMailServer {
  private final Wiser wiser = new Wiser();

  public void start() {
    wiser.start();
  }

  public void setPort(int port) {
    this.wiser.setPort(port);
  }

  public void setHost(String hostName) {
    this.wiser.setHostname(hostName);
  }

  public void stop() {
    wiser.stop();
  }

  public List<WiserMessage> getMessage(){
    return wiser.getMessages();
  }
}
