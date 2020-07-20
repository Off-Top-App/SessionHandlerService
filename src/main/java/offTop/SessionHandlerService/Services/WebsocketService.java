package offTop.SessionHandlerService.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import offTop.SessionHandlerService.Models.IncomingAudioEvent;

@Service
public class WebsocketService{
    private final Logger logger = LoggerFactory.getLogger(WebsocketService.class);

    @Autowired
    ProducerService producerService;

    public int readWebsocketData(Map websocketDataMap){
        logger.info("readWebsocketData(websocketDataMap)");
        int userId = ((Double) websocketDataMap.get("user_id")).intValue();
        if (websocketDataMap.get("topic") != null && websocketDataMap.get("audio_data") != null) {
            triggerIncomingAudioEvent(websocketDataMap);
            
        }
        return userId;
    }

    public void triggerIncomingAudioEvent(Map websocketDataMap){
        logger.info("triggerIncomingAudioEvent(websocketDataMap)");
        int userId = ((Double) websocketDataMap.get("user_id")).intValue();
        String topic = websocketDataMap.get("topic").toString();
        String timeStamp =  websocketDataMap.get("time_exported").toString();
        List<Double> audioData = (ArrayList<Double>) websocketDataMap.get("audio_data");
        IncomingAudioEvent incomingAudioEvent = new IncomingAudioEvent(audioData, userId, timeStamp.toString(), topic);
        producerService.produceIncomingAudioEvent(incomingAudioEvent);
    }
}