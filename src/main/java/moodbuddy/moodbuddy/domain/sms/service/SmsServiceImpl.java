package moodbuddy.moodbuddy.domain.sms.service;

import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
@Slf4j
public class SmsServiceImpl implements SmsService{
    private final DefaultMessageService messageService;
    private static final String USER_MESSAGE = "[moodbuddy] 일기를 작성할 시간이에요! 오늘의 소중한 순간을 쿼디와 함께 기록해볼까요?";
    private static final String LETTER_MESSAGE = "[moodbuddy] 쿼디의 고민 편지 답장이 도착했어요! 어서 확인해보세요 :)";
    private final String senderPhone;

    public SmsServiceImpl(@Value("${coolsms.api-key}")String smsApiKey, @Value("${coolsms.api-secret}") String smsApiSecretKey,
                          @Value("${coolsms.sender-phone}") String senderPhone) {
        this.messageService = NurigoApp.INSTANCE.initialize(smsApiKey, smsApiSecretKey, "https://api.coolsms.co.kr");
        this.senderPhone = senderPhone;
    }

    @Override
    public void sendMessage(String to, String messageType){
        try {
            switch (messageType){
                case "USER" :
                    messageService.send(setMessage(to, USER_MESSAGE));
                    break;
                case "LETTER" :
                    messageService.send(setMessage(to, LETTER_MESSAGE));
                    break;
                default :
                    throw new IllegalArgumentException("Invalid message type.");
            }
        } catch (NurigoMessageNotReceivedException exception) {
            // 발송에 실패한 메시지 목록을 확인
            log.error("exception.getFailedMessageList() : "+exception.getFailedMessageList());
            log.error("exception.getMessage() : "+exception.getMessage());
        } catch (Exception exception) {
            log.error("exception.getMessage() : "+exception.getMessage());
        }
    }

    private Message setMessage(String to, String text){
        Message message = new Message();
        message.setFrom(senderPhone);
        message.setTo(to);
        message.setText(text);

        return message;
    }
}
