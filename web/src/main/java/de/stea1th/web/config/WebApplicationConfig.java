package de.stea1th.web.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@PropertySource("classpath:app-common.properties")
@ComponentScan({"de.stea1th.kafkalibrary.component"})
public class WebApplicationConfig {

}
