package de.stea1th.productservice.config;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@PropertySource("classpath:app-common.properties")
@ComponentScan({"de.stea1th.commonslibrary.component"})
public class ProductServiceApplicationConfig {
}
