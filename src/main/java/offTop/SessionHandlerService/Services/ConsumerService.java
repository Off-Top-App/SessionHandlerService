package offTop.SessionHandlerService.Services;
import com.google.gson.Gson;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import offTop.SessionHandlerService.Components.WebsocketHandler;

import java.util.Map;

@Service
public class ConsumerService {
    private final Logger logger = LoggerFactory.getLogger(ConsumerService.class);
    private WebsocketHandler handler;

    public ConsumerService(WebsocketHandler handler) {
        this.handler = handler;
    }

    public void sendFocusScoreToWebSocket(int userId, String _message) {
        try {
            handler.sendConsumerData(userId, _message);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

