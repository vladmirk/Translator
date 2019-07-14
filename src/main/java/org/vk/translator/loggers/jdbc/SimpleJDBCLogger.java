package org.vk.translator.loggers.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vk.translator.ClientParams;
import org.vk.translator.loggers.LoggingService;
import org.vk.translator.translation.Translation;
import org.vk.translator.translation.TranslationParam;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SimpleJDBCLogger {
  private static final Logger logger = LoggerFactory.getLogger(SimpleJDBCLogger.class);
  private Connection connection;
  private PreparedStatement statement;
  private final ConnectionParams params;

  SimpleJDBCLogger(ConnectionParams params) {
    this.params = params;
  }

  void log(TranslationParam param, ClientParams clientParams, Translation translation) throws SQLException {
    logger.info("Записывем перевод " + translation.getTranslation());
    PreparedStatement statement = getStatement();
    // Query params: TransDate, Client, FromText, FromLang, ToLang, Translation
    statement.setTimestamp(1, new Timestamp(translation.getDate().getTime()));
    statement.setString(2, clientParams.getAddress());
    statement.setString(3, param.getText());
    statement.setString(4, param.getFrom());
    statement.setString(5, param.getTo());
    statement.setString(6, translation.getTranslation());
    statement.executeUpdate();
  }


  private PreparedStatement getStatement() throws SQLException {
    if (statement == null || statement.isClosed()) {
      statement = getConnection().prepareStatement(LoggingService.LOG_QUERY);
    }
    statement.clearParameters();
    return statement;
  }

  private Connection getConnection() throws SQLException {
    if (connection == null || connection.isClosed()) {
      connection = DriverManager.getConnection(params.getUrl(), params.getUser(), params.getPass());
    }
    return connection;
  }

  void destroy() {
    // Нежно закрываем соединения
    if (statement != null) {
      try {
        statement.close();
      } catch (SQLException e) {
        // Не нужно ничего делать
      }
    }

    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        // Не нужно ничего делать
      }
    }
  }
}
