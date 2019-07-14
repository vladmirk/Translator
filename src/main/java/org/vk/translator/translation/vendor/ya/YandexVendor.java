package org.vk.translator.translation.vendor.ya;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.vk.translator.translation.SimpleTranslation;
import org.vk.translator.translation.Translation;
import org.vk.translator.translation.TranslationParam;
import org.vk.translator.translation.TranslatorVendor;
import org.vk.translator.translation.vendor.Vendor;


@Component(value = Vendor.YANDEX)
public class YandexVendor implements TranslatorVendor {

  private final RestTemplate restTemplate;

  @Autowired
  public YandexVendor(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build(RestTemplate.class);
  }

  @Value("${yandex.key}")
  private String yandexKey;

  @Override
  public Translation translate(TranslationParam param) {
    String url = "https://translate.yandex.net/api/v1.5/tr.json/translate";
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    MultiValueMap<String, String> postParams = new LinkedMultiValueMap<>();
    postParams.add("text", param.getText());
    postParams.add("lang", param.getFrom() + "-" + param.getTo());
    postParams.add("key", yandexKey);
    postParams.add("format", "plain");

    HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(postParams, headers);
    ResponseEntity<YandexTranslation> yandexTranslationResponseEntity = restTemplate.postForEntity(url, request, YandexTranslation.class);
    return HttpStatus.OK == yandexTranslationResponseEntity.getStatusCode() ? yandexTranslationResponseEntity.getBody() : new SimpleTranslation();
  }
}
