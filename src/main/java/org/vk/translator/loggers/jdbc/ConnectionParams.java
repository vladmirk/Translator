package org.vk.translator.loggers.jdbc;


public class ConnectionParams {
  private String url, user, pass;

  public ConnectionParams(String url, String user, String pass) {
    this.url = url;
    this.user = user;
    this.pass = pass;
  }

  public String getUrl() {
    return url;
  }
  public void setUrl(String url) {
    this.url = url;
  }
  public String getUser() {
    return user;
  }
  public void setUser(String user) {
    this.user = user;
  }
  public String getPass() {
    return pass;
  }
  public void setPass(String pass) {
    this.pass = pass;
  }
}
