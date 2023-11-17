package demo;

import com.codahale.metrics.ConsoleReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import ai.applica.spring.boot.starter.temporal.annotations.EnableTemporal;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

@EnableTemporal
@SpringBootApplication
@EnableScheduling
public class Application {

  //static final MetricRegistry registry = new MetricRegistry();

  public static void main(String[] args) {
    //startReport();


    SpringApplication.run(Application.class, args);
  }

  /*static void startReport() {
    SharedMetricRegistries.add("objectRunnerRegistryName", registry);

    ConsoleReporter reporter = ConsoleReporter.forRegistry(registry)
            .convertRatesTo(TimeUnit.SECONDS)
            .convertDurationsTo(TimeUnit.MILLISECONDS)
            .outputTo(new PrintStream(System.out))
            .build();
    reporter.start(3, TimeUnit.SECONDS);
  }

   */
}