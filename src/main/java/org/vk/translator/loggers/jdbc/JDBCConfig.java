package org.vk.translator.loggers.jdbc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JDBCConfig {

  @Value("${spring.datasource.url}")
  private String dbUrl;

  @Value("${spring.datasource.username}")
  private String user;

  @Value("${spring.datasource.password}")
  private String pass;

  @Bean
  public ConnectionParams connectionParams() {
    return new ConnectionParams(dbUrl, user, pass);
  }

}
