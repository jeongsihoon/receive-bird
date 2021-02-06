package jeong.sihoon.receive.bird.websocket.handler;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketSession;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class WebSocketSessionManager {
    @Getter
    final private Set<WebSocketSession> webSocketSessionSet = ConcurrentHashMap.newKeySet();

    public void registerSession(WebSocketSession webSocketSession) {
        webSocketSessionSet.add(webSocketSession);
    }

    public void deregisterSession(WebSocketSession webSocketSession) {
        webSocketSessionSet.remove(webSocketSession);
    }
}
