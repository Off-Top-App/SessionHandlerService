package offTop.SessionHandlerService.Models;

public class IncomingAudioEvent {
    private String audioData;
    private int userId;
    private String timeStamp;
    private String topic;

    public IncomingAudioEvent() {}

    public IncomingAudioEvent(double userId, String timeStamp, String topic) {
        this.userId = (int) Math.round(userId);
        this.timeStamp = timeStamp;
        this.topic = topic;
    }

    public IncomingAudioEvent(String audioData, double userId, String timeStamp, String topic) {
        this.audioData = audioData;
        this.userId = (int) Math.round(userId);
        this.timeStamp = timeStamp;
        this.topic = topic;
    }

    public String getAudioData() {
        return this.audioData;
    }

    public int getUserId() {
        return this.userId;
    }

    public String getTimeStamp() {
        return this.timeStamp;
    }

    public String getTopic() {
        return this.topic;
    }


    public void setAudioData(String audioData) {
        this.audioData = audioData;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public String toString() {
        return "Audio-Event{" + "audioData='" + audioData + '\'' + ", userId='" + userId + '\'' + ", timeStamp="
                + timeStamp.toString() + '\'' + ", topic = " + topic + '\'' + '}';
    }
}