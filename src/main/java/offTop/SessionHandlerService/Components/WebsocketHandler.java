package offTop.SessionHandlerService.Components;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.google.gson.Gson;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import offTop.SessionHandlerService.Models.IncomingAudioEvent;
import offTop.SessionHandlerService.Services.WebsocketService;

@Component
public class WebsocketHandler<T> extends TextWebSocketHandler {

    @Autowired
    private WebsocketService websocketService;

    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    Map<Integer, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        for (int i = 0; i < sessions.size(); i++) {
            WebSocketSession webSocketSession; // = (WebSocketSession) sessions.get(i);

            Map<String, T> value = new Gson().fromJson(message.getPayload(), Map.class);
            int userId = ((Integer) value.get("user_id")).intValue();
            LocalDateTime timeStamp = LocalDateTime.now();
            if (value.get("topic") != null && value.get("audio_data") != null) {
                String topic = value.get("topic").toString();
                String audioData = value.get("audio_data").toString();
               IncomingAudioEvent incomingAudioEvent = new IncomingAudioEvent(audioData, userId, timeStamp.toString(), topic);
                websocketService.handleIncomingMessages((ArrayList<Double>) value.get("audio_data"), incomingAudioEvent);
            }
            // if the user connects to the websocket for the first time
            if (!userSessions.containsKey(userId)) {
                userSessions.put(userId, session);
            }
            webSocketSession = userSessions.get(userId);

            TextMessage textMessage = new TextMessage("Received Incoming Audio data!");
            try {
                webSocketSession.sendMessage(textMessage);
            } catch (Exception ex) {
                synchronized (sessions) {
                    System.out.println(ex);
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
        System.out.println("CREATED SESSION: " + session.toString());
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessions.remove(session);
        System.out.println("CLOSED CONNECTION: " + status.toString());
        super.afterConnectionClosed(session, status);
    }

}