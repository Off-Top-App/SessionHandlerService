package offTop.SessionHandlerService.Services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import offTop.SessionHandlerService.Models.IncomingAudioEvent;

@Service
public class WebsocketService {

    @Autowired
    AudioService audioService;

    public void handleIncomingMessages(ArrayList<Double> audioData, IncomingAudioEvent data){
        audioService.convertToByteDataAndProduceMessage(audioData, data);
    }
}
