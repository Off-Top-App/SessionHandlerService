package offTop.SessionHandlerService.Utils;

import org.springframework.stereotype.Service;

@Service
public class MessageParserService{
    public String parseMessage(String incomingMessage){
        incomingMessage = incomingMessage.substring(1, incomingMessage.length() - 1);
        return incomingMessage.replaceAll("\\\\\"", "\"");
    }
}