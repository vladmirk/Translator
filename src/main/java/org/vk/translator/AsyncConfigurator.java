package org.vk.translator;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfigurator extends AsyncConfigurerSupport {
  private static final Logger logger = LoggerFactory.getLogger(AsyncConfigurator.class);

  @Value("${translator.thread.qnty}")
  private Integer threadQnty;


  @Bean(name = "translatorExecutor")
  public Executor translatorExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(threadQnty);
    executor.setMaxPoolSize(threadQnty);
    executor.setThreadNamePrefix("Translator-");
    executor.initialize();
    return executor;
  }

  @Override
  public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
    return (throwable, method, obj) -> {
      StringBuilder sb = new StringBuilder();
      sb.append("Иключение в Потоке - ").append(Thread.currentThread().getName());
      sb.append("\n Сообщение: ").append(throwable.getMessage());
      sb.append("\n Метод: ").append(method.getName()).append('-');
      for (Object param : obj) {
        sb.append(param).append(';');
      }
      logger.error(sb.toString());
    };
  }

}
