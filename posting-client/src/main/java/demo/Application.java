package demo;

import ai.applica.spring.boot.starter.temporal.annotations.EnableTemporal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableTemporal
@SpringBootApplication
@EnableScheduling
public class Application {

  public static void main(String[] args) {

    SpringApplication.run(Application.class, args);
  }

}