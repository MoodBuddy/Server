package moodbuddy.moodbuddy.external.sms;

public interface SmsService {
    void sendMessage(String to, String messageType);
}
