package offTop.SessionHandlerService.KafkaListeners;
import offTop.SessionHandlerService.Services.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


import com.google.gson.Gson;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import offTop.SessionHandlerService.Components.WebsocketHandler;

import java.util.Map;

public class KafkaConsumer {
    private final Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);
    @Autowired
    ConsumerService consumerService;
    // Receives message from the producer and displays into console
    // Kafka Listner subscribe to a topic and receives all the message sent into that
    // topic
    @KafkaListener(topics = "outgoingFocusScore", groupId = "group_Id")
    public void receiveFocusScore(String message) {
        message = message.substring(1, message.length() - 1);
        String _message = message.replaceAll("\\\\\"", "\"");
        System.out.println("_message: " + _message);
        Map value = new Gson().fromJson(_message, Map.class);
        logger.info(String.format("The message you entered -> %s", value.toString()));
        double userId = (double) value.get("user_id");
        consumerService.sendFocusScoreToWebSocket(userId, _message);
    }
}
