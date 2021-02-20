package jeong.sihoon.receive.bird.websocket.handler;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

@Configuration
public class WebSocketHandlerConfiguration {
    @NotNull
    @Value("${receive-bird.websocket-handler.node-id}")
    private Integer nodeId;
}
