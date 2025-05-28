package ru.testtask.webapp.config;

import org.hibernate.boot.model.naming.ImplicitNamingStrategy;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyComponentPathImpl;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaConfig {

    @Bean
    public PhysicalNamingStrategy physicalNamingStrategy() {
        return new JpaNamingStrategyConfig();
    }

    @Bean
    public ImplicitNamingStrategy implicitNamingStrategy() {
        return new ImplicitNamingStrategyComponentPathImpl();
    }
}
