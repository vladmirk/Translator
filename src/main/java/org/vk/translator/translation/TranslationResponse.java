package org.vk.translator.translation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TranslationResponse {
  private String text;
  private String copyright;

  public TranslationResponse() {
  }

  public TranslationResponse(String text, String copyright) {
    this.text = text;
    this.copyright = copyright;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getCopyright() {
    return copyright;
  }

  public void setCopyright(String copyright) {
    this.copyright = copyright;
  }
}
