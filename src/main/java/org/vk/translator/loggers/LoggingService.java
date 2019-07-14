package org.vk.translator.loggers;

import org.vk.translator.ClientParams;
import org.vk.translator.translation.Translation;
import org.vk.translator.translation.TranslationParam;

public interface LoggingService {
  String LOG_QUERY = "insert into TransLog (TransDate, Client, FromText, FromLang, ToLang, Translation) values (?,?,?,?,?,?)";
  void log(TranslationParam param, ClientParams clientParams, Translation translation);

}
