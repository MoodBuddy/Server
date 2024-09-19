package moodbuddy.moodbuddy.domain.sms.service;

public interface SmsService {
    void sendMessage(String to, String messageType);
}
