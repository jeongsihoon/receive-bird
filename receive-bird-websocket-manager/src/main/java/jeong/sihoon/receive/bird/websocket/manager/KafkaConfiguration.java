package jeong.sihoon.receive.bird.websocket.manager;

import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.function.Consumer;

@Log4j2
@Configuration
public class KafkaConfiguration {
    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;

    @Bean
    public Consumer<String> sink1() {
        return message->{
            kafkaTemplate.send("relay", message);
            log.info("ReceiveMessage: {}", message);
        };
    }
}
