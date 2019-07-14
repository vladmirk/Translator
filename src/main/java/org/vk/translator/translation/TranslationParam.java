package org.vk.translator.translation;

public class TranslationParam {
  private String text, from, to;

  public TranslationParam() {
  }

  public TranslationParam(String from, String to, String text) {
    this.text = text;
    this.from = from;
    this.to = to;
  }

  public String getText() {
    return text;
  }
  public void setText(String text) {
    this.text = text;
  }
  public String getFrom() {
    return from;
  }
  public void setFrom(String from) {
    this.from = from;
  }
  public String getTo() {
    return to;
  }
  public void setTo(String to) {
    this.to = to;
  }

  @Override
  public String toString() {
    return "'" + text + '\'' + ", '" + from + "->" + to + '\'';
  }
}
