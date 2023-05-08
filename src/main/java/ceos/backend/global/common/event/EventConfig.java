package ceos.backend.global.common.event;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @Bean
    public InitializingBean eventsInitializer() {
        return () -> Event.setPublisher(applicationEventPublisher);
    }
}
