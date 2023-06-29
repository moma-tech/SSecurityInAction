package top.mmtech.ssinaction.config;

import java.util.concurrent.Executor;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.concurrent.DelegatingSecurityContextExecutor;

@SpringBootConfiguration
@EnableAsync
public class SpringConfig {
  @Bean("taskExecutor")
  public Executor taskExecutor() {
    SimpleAsyncTaskExecutor executor = new SimpleAsyncTaskExecutor("zoffy-async-");
    return new DelegatingSecurityContextExecutor(executor);
  }
}
