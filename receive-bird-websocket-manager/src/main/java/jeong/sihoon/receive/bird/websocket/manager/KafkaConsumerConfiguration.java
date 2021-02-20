package jeong.sihoon.receive.bird.websocket.manager;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

@Log4j2
@Configuration
public class KafkaConsumerConfiguration implements InitializingBean {
    @Autowired
    private KafkaTemplate<Object, Object> kafkaTemplate;

    @Autowired
    private WebSocketManagerConfiguration webSocketManagerConfiguration;


    @Getter
    private final Map<String, Integer> sessionNodeIdMap = new HashMap<>();

    public static final String NODE_ID = "NODE_ID";
    public static final String SESSION_ID = "SESSION_ID";
    public static final String GROUP_ID = "GROUP_ID";
    public static final String MESSAGE = "MESSAGE";

    private static String TOPIC_SEND_MESSAGE_TO_USER;

    @Override
    public void afterPropertiesSet() {
        TOPIC_SEND_MESSAGE_TO_USER = "receive.bird.websocket.handler.send.message.user."
                + webSocketManagerConfiguration.getNodeId();
    }

    @Bean
    public Consumer<String> sendMessageToUser() {
        return message->{
            JSONObject data = new JSONObject(message);
            String id = data.getString(SESSION_ID);
            Integer nodeId = sessionNodeIdMap.get(id);
            if (nodeId != null) {
                kafkaTemplate.send(TOPIC_SEND_MESSAGE_TO_USER, message);
            }
            log.info("[SEND MESSAGE TO USER] {} {}", nodeId, message);
        };
    }

    @Bean
    public Consumer<String> registerSession() {
        return message->{
            JSONObject data = new JSONObject(message);
            sessionNodeIdMap.put(data.getString(SESSION_ID), data.getInt(NODE_ID));
            log.info("[REGISTER SESSION] {}", message);
        };
    }

    @Bean
    public Consumer<String> deregisterSession() {
        return message->{
            JSONObject data = new JSONObject(message);
            sessionNodeIdMap.remove(data.getString(SESSION_ID));
            log.info("[DEREGISTER SESSION] {}", message);
        };
    }
}
