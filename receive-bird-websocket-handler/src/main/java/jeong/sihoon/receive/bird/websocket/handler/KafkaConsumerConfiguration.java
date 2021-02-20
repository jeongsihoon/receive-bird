package jeong.sihoon.receive.bird.websocket.handler;

import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.web.reactive.socket.WebSocketMessage;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Log4j2
@Configuration
public class KafkaConsumerConfiguration {
    @Autowired
    private WebSocketSessionManager webSocketSessionManager;

    @Bean
    public Consumer<String> sendMessageToAll() {
        return message->{
            log.info("[SEND MESSAGE TO ALL]: {}", message);

            WebSocketMessage relayMessage = new WebSocketMessage(WebSocketMessage.Type.TEXT,
                    DefaultDataBufferFactory.sharedInstance.wrap(message.getBytes()));

            webSocketSessionManager.getWebSocketSessionSet()
                    .forEach(session->session.send(Mono.just(relayMessage)).subscribe());
        };
    }

    @Bean
    public Consumer<String> sendMessageToUser() {
        return message->{
            log.info("[SEND MESSAGE TO ALL]: {}", message);
            JSONObject data = new JSONObject();
            WebSocketMessage relayMessage = new WebSocketMessage(WebSocketMessage.Type.TEXT,
                    DefaultDataBufferFactory.sharedInstance.wrap(message.getBytes()));

            webSocketSessionManager.getWebSocketSessionSet()
                    .forEach(session->session.send(Mono.just(relayMessage)).subscribe());
        };
    }
}
