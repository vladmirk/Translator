package org.vk.translator.translation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Primary;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.vk.translator.ClientParams;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@Primary
public class TranslateByWordsService implements TranslationService {
  @Qualifier("messageSource")
  @Autowired
  private MessageSource msgSource;

  @Autowired
  @Qualifier("asyncTranslator")
  private TranslationService service;

  @Override
  public Translation translate(TranslationParam param, ClientParams clientParams) {
    List<CompletableFuture<Translation>> translations = Arrays.stream(param.getText().split("[.,;\\s]+"))
        .map(aWord -> service.translateAsync(new TranslationParam(param.getFrom(), param.getTo(), aWord), clientParams)).collect(Collectors.toList());

    // О пунктуации все равно никто не забодится. Поэтому мы ее потеряем с чистой совестью....
    String translation = translations.stream().map(CompletableFuture::join).map(t -> t.getTranslation()).collect(Collectors.joining(" "));

    String copyright = translations.isEmpty() ? "" : translations.get(0).join().getCopyright();
    return new SimpleTranslation(translation, copyright);
  }

  @Override
  public CompletableFuture<Translation> translateAsync(TranslationParam translationParam, ClientParams clientParams) {
    throw new UnsupportedOperationException(msgSource.getMessage("nomultithread", null, LocaleContextHolder.getLocale()));
  }
}
