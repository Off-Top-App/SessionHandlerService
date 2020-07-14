package offTop.SessionHandlerService.KafkaListeners;

import java.util.Map;

import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import offTop.SessionHandlerService.Services.ConsumerService;
import offTop.SessionHandlerService.Utils.MessageParser;

public class FocusScoreConsumer {
    private final Logger logger = LoggerFactory.getLogger(FocusScoreConsumer.class);
    @Autowired
    ConsumerService consumerService;
    // Receives message from the producer and displays into console
    // Kafka Listner subscribe to a topic and receives all the message sent into that
    // topic
    @KafkaListener(topics = "outgoingFocusScore", groupId = "group_Id")
    public void receiveFocusScore(String message) {
        MessageParser messageParser = new MessageParser();
        message = messageParser.parseMessage(message);
        Map value = new Gson().fromJson(message, Map.class);
        logger.info("Consumed FocusScoreEvent");
        int userId = ((Double) value.get("user_id")).intValue();
        consumerService.sendFocusScoreToWebSocket(userId, message);
    }
}
