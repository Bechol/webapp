package ru.testtask.webapp.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String botToken;
    private String cryptoKey;
    private Long hashLiveTime;
}
