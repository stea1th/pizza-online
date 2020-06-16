package de.stea1th.commonslibrary;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

@SpringBootApplication
public class CommonsLibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonsLibraryApplication.class, args);
    }

    @Bean
    public ConcurrentMessageListenerContainer<Long, String> repliesContainer(
            @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") ConcurrentKafkaListenerContainerFactory<Long, String> containerFactory) {

        ConcurrentMessageListenerContainer<Long, String> repliesContainer =
                containerFactory.createContainer("replies");
        repliesContainer.getContainerProperties().setGroupId("repliesGroup");
        repliesContainer.setAutoStartup(false);
        return repliesContainer;
    }

    @Bean
    public ReplyingKafkaTemplate<String, String, String> replyingTemplate(
            @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection") ProducerFactory<Long, String> pf,
            ConcurrentMessageListenerContainer<Long, String> repliesContainer) {

        return new ReplyingKafkaTemplate(pf, repliesContainer);
    }
}
