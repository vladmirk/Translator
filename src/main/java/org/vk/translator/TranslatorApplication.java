package org.vk.translator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TranslatorApplication {

  public static void main(String[] args) {
    SpringApplication.run(TranslatorApplication.class, args);
  }

  /**
   * Оставил в качестве пример альтернативной инициации бинов.
   * (На случай, если не нравится suppress warning @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") в AsyncTranslationService)
   */
//  @Bean
//  @ConditionalOnProperty(name = "translator.vendor", havingValue = "YANDEX")
//  public TranslatorVendor translationVendor(RestTemplateBuilder restTemplateBuilder) {
//    return new YandexVendor(restTemplateBuilder.build(RestTemplate.class));
//  }
//
//  @Bean
//  @Primary
//  @ConditionalOnProperty(name = "translator.vendor", havingValue = "TRANSLIT")
//  public TranslatorVendor translitVendor() {
//    return new TranslitVendor();
//  }
}
