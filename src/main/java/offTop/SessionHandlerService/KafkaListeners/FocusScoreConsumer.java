package offTop.SessionHandlerService.KafkaListeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import offTop.SessionHandlerService.Services.ConsumerService;
import offTop.SessionHandlerService.Utils.MessageParserService;

@Service
public class FocusScoreConsumer {
    private final Logger logger = LoggerFactory.getLogger(FocusScoreConsumer.class);
    @Autowired
    ConsumerService consumerService;
    @Autowired
    MessageParserService messageParserService;
    
    @KafkaListener(topics = "outgoingFocusScore", groupId = "FocusScore")
    public void consumeOutgoingFocusScoreEvent(String message) {
        logger.info("consumeOutgoingFocusScoreEvent()");
        consumerService.readFocusScoreEventData(message);
    }
}
