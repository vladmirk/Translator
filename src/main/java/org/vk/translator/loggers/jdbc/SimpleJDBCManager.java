package org.vk.translator.loggers.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SimpleJDBCManager {

  @Autowired
  private ConnectionParams params;

  private final ThreadLocal<SimpleJDBCLogger> LOGGERS = new ThreadLocal<SimpleJDBCLogger>() {
    @Override
    protected SimpleJDBCLogger initialValue() {
      return new SimpleJDBCLogger(params);
    }
  };

  public SimpleJDBCLogger logger() {
    return LOGGERS.get();
  }

}
