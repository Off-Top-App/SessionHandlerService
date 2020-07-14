package offTop.SessionHandlerService.Utils;

public class MessageParser{
    public String parseMessage(String incomingMessage){
        incomingMessage = incomingMessage.substring(1, incomingMessage.length() - 1);
        return incomingMessage.replaceAll("\\\\\"", "\"");
    }
}