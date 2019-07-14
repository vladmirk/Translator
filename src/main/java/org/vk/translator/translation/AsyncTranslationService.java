package org.vk.translator.translation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.vk.translator.ClientParams;
import org.vk.translator.loggers.LoggingService;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service("asyncTranslator")
public class AsyncTranslationService implements TranslationService {
  private static final Logger logger = LoggerFactory.getLogger(AsyncTranslationService.class);

  @Qualifier("messageSource")
  @Autowired
  private MessageSource msgSource;


  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Resource(name = "${translator.vendor}")
  private TranslatorVendor translatorVendor;

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Resource(name = "${translator.logger}")
  private LoggingService loggingService;


  @Override
  @Async("translatorExecutor")
  @Transactional
  public CompletableFuture<Translation> translateAsync(TranslationParam param, ClientParams clientParams) {
    logger.info(msgSource.getMessage("translating", new String[]{param.toString()}, LocaleContextHolder.getLocale()));
    try {
      Translation response = translatorVendor.translate(param);
      loggingService.log(param, clientParams, response);
      return CompletableFuture.completedFuture(response);
    } catch (RestClientException e) {
      logger.error(msgSource.getMessage("oops", null, LocaleContextHolder.getLocale()), e);
      return CompletableFuture.completedFuture(new SimpleTranslation());
    }
  }

  @Override
  public Translation translate(TranslationParam translationParam, ClientParams clientParams) {
    try {
      return translateAsync(translationParam, clientParams).get();
    } catch (InterruptedException | ExecutionException e) {
      logger.error(msgSource.getMessage("oops", null, LocaleContextHolder.getLocale()), e);
      return new SimpleTranslation(translationParam.getText());
    }
  }

}
