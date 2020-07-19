package offTop.SessionHandlerService.KafkaListeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import offTop.SessionHandlerService.Models.IncomingAudioEvent;

@Service
public class AudioEventProducer{
    private static final Logger logger = LoggerFactory.getLogger(AudioEventProducer.class);
    private static final String TOPIC = "outgoingAudioEvent";

    @Autowired
    private KafkaTemplate<String, IncomingAudioEvent> kafkaTemplate;

    public void sendIncomingAudioEvent(IncomingAudioEvent incomingAudioEvent) {
        logger.info("sendIncomingAudioEvent(incomingAudioEvent)");
        kafkaTemplate.send(TOPIC, incomingAudioEvent);
        logger.info(String.format("Producing audioEvent"));
     }
}