package moodbuddy.moodbuddy.global.common.sms.service;

public interface SmsService {
    void sendMessage(String to, String messageType);
}
