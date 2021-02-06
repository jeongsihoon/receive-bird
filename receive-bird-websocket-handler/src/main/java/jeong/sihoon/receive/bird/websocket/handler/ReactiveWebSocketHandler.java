package jeong.sihoon.receive.bird.websocket.handler;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.DefaultKafkaConsumerFactoryCustomizer;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.DefaultDataBinderFactory;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Log4j2
@Component
public class ReactiveWebSocketHandler implements WebSocketHandler {
    @Autowired
    private WebSocketSessionManager webSocketSessionManager;

    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;

    @Bean
    public Consumer<String> sink1() {
        return message->{
            log.info("ReceiveMessage: {}", message);
            WebSocketMessage relayMessage = new WebSocketMessage(WebSocketMessage.Type.TEXT,
                    DefaultDataBufferFactory.sharedInstance.wrap(message.getBytes()));

            webSocketSessionManager.getWebSocketSessionSet()
                    .forEach(session->session.send(Mono.just(relayMessage)).subscribe());
        };
    }

    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        return webSocketSession.receive()
                .doOnSubscribe(subscription -> {
                    log.info("[SUBSCRIBE]");
                    webSocketSessionManager.registerSession(webSocketSession);
                })
                .doOnNext(webSocketMessage -> {
                    kafkaTemplate.send("test", webSocketMessage.getPayloadAsText());
                    log.info("[NEXT]");
                })
                .doOnComplete(()->{
                    log.info("[COMPLETE]");
                })
                .doOnCancel(()->{
                    log.info("[CANCEL]");
                })
                .doOnError(error->{
                    log.info(error);
                })
                .then();
    }
}
