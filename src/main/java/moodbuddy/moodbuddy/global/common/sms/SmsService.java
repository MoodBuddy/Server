package moodbuddy.moodbuddy.global.common.sms;

public interface SmsService {
    void sendMessage(String to, String messageType);
}
