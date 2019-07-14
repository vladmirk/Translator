package org.vk.translator.loggers.springjdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.vk.translator.ClientParams;
import org.vk.translator.loggers.DBLogger;
import org.vk.translator.loggers.LoggingService;
import org.vk.translator.translation.Translation;
import org.vk.translator.translation.TranslationParam;

import java.util.Date;

@Service(value = DBLogger.SPRING_JDBC)
public class SpringJDBCLoggingService implements LoggingService {
  private static final Logger logger = LoggerFactory.getLogger(SpringJDBCLoggingService.class);

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Async
  @Override
  public void log(TranslationParam param, ClientParams clientParams, Translation translation) {
    logger.info(String
        .format("%tT Клиент %s запросил перевод'%s' с %s на %s: %s", new Date(), clientParams.getAddress(), param.getText(), param.getFrom(),
            param.getTo(), translation.getTranslation()));

    jdbcTemplate.update(LOG_QUERY, translation.getDate(), clientParams.getAddress(), param.getText(), param.getFrom(), param.getTo(),
        translation.getTranslation());
  }

}
