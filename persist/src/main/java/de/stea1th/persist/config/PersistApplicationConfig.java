package de.stea1th.persist.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@PropertySource("classpath:app-common.properties")
public class PersistApplicationConfig {
}
