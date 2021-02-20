package jeong.sihoon.receive.bird.websocket.handler;

import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Log4j2
@Component
public class ReactiveWebSocketHandler implements WebSocketHandler {
    @Autowired
    private WebSocketSessionManager webSocketSessionManager;

    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;



    @Override
    public Mono<Void> handle(WebSocketSession webSocketSession) {
        return webSocketSession.receive()
                .doOnSubscribe(subscription -> {
                    log.info("[SUBSCRIBE]");
                    webSocketSessionManager.registerSession(webSocketSession);
                    JSONObject registerSessionMessage = new JSONObject();
                    registerSessionMessage.put("NODE_ID", 1);
                    registerSessionMessage.put("SESSION_ID", UUID.randomUUID().toString());
                    kafkaTemplate.send("register_session", registerSessionMessage.toString());
                })
                .doOnNext(webSocketMessage -> {
                    log.info("[NEXT]");
                    kafkaTemplate.send("send_message_to_all", webSocketMessage.getPayloadAsText());
                })
                .doOnComplete(()->{
                    log.info("[COMPLETE]");
                    webSocketSessionManager.deregisterSession(webSocketSession);
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
