package jeong.sihoon.receive.bird.websocket.manager;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;

@Log4j2
@Configuration
@Getter
public class WebSocketManagerConfiguration {
    @NotNull @Value("${receive-bird.websocket-manager.node-id}")
    private Integer nodeId;

}