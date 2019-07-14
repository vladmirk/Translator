package org.vk.translator.translation;

import java.util.Date;

public class SimpleTranslation implements Translation {
  private final String text;
  private final String copyright;
  private final Date date;

  public SimpleTranslation(String text) {
    this(text, "Само как-то перевелось...");
  }

  public SimpleTranslation(String text, String copyright) {
    this.text = text;
    this.copyright = copyright;
    this.date = new Date();
  }

  public SimpleTranslation() {
    this("Ooops");
  }

  @Override
  public String getTranslation() {
    return text;
  }

  @Override
  public String getCopyright() {
    return copyright;
  }

  @Override
  public Date getDate() {
    return date;
  }
}
