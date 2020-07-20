package offTop.SessionHandlerService.Services;

import java.io.IOException;
import java.util.Map;

import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import offTop.SessionHandlerService.Components.WebsocketHandler;
import offTop.SessionHandlerService.Utils.MessageParserService;

@Service
public class ConsumerService {
    private final Logger logger = LoggerFactory.getLogger(ConsumerService.class);
    private WebsocketHandler handler;

    @Autowired
    MessageParserService messageParserService;


    public ConsumerService(WebsocketHandler handler) {
        this.handler = handler;
    }

    public void readFocusScoreEventData(String consumedMessage){
        logger.info("getFocusScoreEventData()");
        consumedMessage = messageParserService.parseMessage(consumedMessage);
        Map value = new Gson().fromJson(consumedMessage, Map.class);
        logger.info("Consumed FocusScoreEvent");
        int userId = ((Double) value.get("user_id")).intValue();
        sendFocusScoreToWebSocket(userId, consumedMessage);
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

