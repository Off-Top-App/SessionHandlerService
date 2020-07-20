package offTop.SessionHandlerService.Services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import offTop.SessionHandlerService.KafkaListeners.AudioEventProducer;
import offTop.SessionHandlerService.Models.IncomingAudioEvent;

@Service
public class ProducerService {

    private static final Logger logger = LoggerFactory.getLogger(ProducerService.class);

    @Autowired
    AudioEventProducer audioEventProducer;

    public void produceIncomingAudioEvent(IncomingAudioEvent audioEvent) {
        logger.info("produceIncomingAudioEvent(audioEvent)");
        audioEventProducer.sendIncomingAudioEvent(audioEvent);
     }

}