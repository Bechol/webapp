package ru.testtask.webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.testtask.webapp.config.AppProperties;

@SpringBootApplication
@EnableConfigurationProperties({AppProperties.class})
public class WebappApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebappApplication.class, args);
	}

}
