package org.vk.translator.translation.vendor.ya;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.vk.translator.translation.Translation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class YandexTranslation implements Translation {
  private Integer code;
  private String lang;
  private Date date;
  private List<String> text = new ArrayList<>();


  public YandexTranslation() {
  }

  public YandexTranslation(String text) {
    this.text.add(text);
    date = new Date();
  }

  public Integer getCode() {
    return code;
  }
  public void setCode(Integer code) {
    this.code = code;
  }
  public String getLang() {
    return lang;
  }
  public void setLang(String lang) {
    this.lang = lang;
  }
  public List<String> getText() {
    return text;
  }
  public void setText(List<String> text) {
    this.text = text;
    date = new Date();
  }

  @Override
  @JsonIgnore
  public String getTranslation() {
    return getText().stream().collect(Collectors.joining(" "));
  }

  @Override
  @JsonIgnore
  public String getCopyright() {
    return "Переведено сервисом <a href=\"http://translate.yandex.ru/\">«Яндекс.Переводчик»</a>";
  }

  @Override
  public Date getDate() {
    return date;
  }
}
