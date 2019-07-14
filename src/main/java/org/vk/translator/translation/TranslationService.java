package org.vk.translator.translation;

import org.vk.translator.ClientParams;

import java.util.concurrent.CompletableFuture;

public interface TranslationService {

  Translation translate(TranslationParam translationParam, ClientParams clientParams);
  CompletableFuture<Translation> translateAsync(TranslationParam translationParam, ClientParams clientParams);
}
