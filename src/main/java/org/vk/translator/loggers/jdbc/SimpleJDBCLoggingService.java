package org.vk.translator.loggers.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.vk.translator.ClientParams;
import org.vk.translator.loggers.DBLogger;
import org.vk.translator.loggers.LoggingService;
import org.vk.translator.translation.Translation;
import org.vk.translator.translation.TranslationParam;

import javax.annotation.PreDestroy;
import java.sql.SQLException;
import java.util.Date;

@Service(value = DBLogger.PURE_JDBC)
public class SimpleJDBCLoggingService implements LoggingService {
  private static final Logger logger = LoggerFactory.getLogger(SimpleJDBCLoggingService.class);
  @Autowired
  private SimpleJDBCManager manager;

  @Async
  @Override
  public void log(TranslationParam param, ClientParams clientParams, Translation translation) {
    logger.info(String
        .format("%tT Client %s requested translating '%s' from %s to %s: %s", new Date(), clientParams.getAddress(), param.getText(), param.getFrom(),
            param.getTo(), translation.getTranslation()));
    try {
      manager.logger().log(param, clientParams, translation);
    } catch (SQLException e) {
      logger.error("Что-то пошло не так", e);
    }
  }

  @PreDestroy
  public void closeConnections() {
    manager.logger().destroy();
  }

}
