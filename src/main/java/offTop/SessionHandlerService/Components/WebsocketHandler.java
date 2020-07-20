package offTop.SessionHandlerService.Components;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import offTop.SessionHandlerService.Models.IncomingAudioEvent;
import offTop.SessionHandlerService.Services.ProducerService;
import offTop.SessionHandlerService.Services.WebsocketService;

@Component
public class WebsocketHandler<T> extends TextWebSocketHandler {
    private final Logger logger = LoggerFactory.getLogger(WebsocketHandler.class);

    @Autowired
    private WebsocketService websocketService;
    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    Map<Integer, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        for (int i = 0; i < sessions.size(); i++) {
            WebSocketSession webSocketSession; // = (WebSocketSession) sessions.get(i);
            Map<String, T> websocketDataMap = new Gson().fromJson(message.getPayload(), Map.class);
            int userId = websocketService.readWebsocketData(websocketDataMap);
            if (!userSessions.containsKey(userId)) {
                userSessions.put(userId, session);
            }
            webSocketSession = userSessions.get(userId);

            TextMessage textMessage = new TextMessage("Received Incoming Audio data!");
            try {
                webSocketSession.sendMessage(textMessage);
            } catch (Exception ex) {
                synchronized (sessions) {
                    logger.error(ex.toString());
                }
            }
        }
    }

    public void sendConsumerData(int userId, String message) throws IOException {
        TextMessage textMessage = new TextMessage(message);
        if (userSessions.containsKey(userId) == true) {
            WebSocketSession s = userSessions.get(userId);
            if (s.isOpen()) {
                s.sendMessage(textMessage);
            } else {
                userSessions.remove(userId);
            }
        }
    }

    @Override
    public void afterConnectionEstablished(final WebSocketSession session) throws Exception {
        session.setTextMessageSizeLimit(1024 * 1024);
        session.setBinaryMessageSizeLimit(1024 * 1024);
        logger.info("CREATED SESSION: " + session.toString());
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        logger.info("CLOSED CONNECTION: " + status.toString());
        super.afterConnectionClosed(session, status);
    }

}