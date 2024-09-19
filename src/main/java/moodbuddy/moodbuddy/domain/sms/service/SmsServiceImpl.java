package moodbuddy.moodbuddy.domain.sms.service;

import lombok.RequiredArgsConstructor;
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
    @Value("${coolsms.api-key}")
    private String smsApiKey;
    @Value("${coolsms.api-secret}")
    private String smsApiSecretKey;
    @Value("${coolsms.sender-phone}")
    private String senderPhone;

    private final DefaultMessageService messageService;

    public SmsServiceImpl(){
        this.messageService = NurigoApp.INSTANCE.initialize(smsApiKey, smsApiSecretKey, "https://api.coolsms.co.kr");
    }

    @Override
    public void sendMessage(String to){
        try {
            messageService.send(setMessage(to)); // 컨트롤러에서 30초 이후에 자동으로 호출하니까 sms는 따로 지연 시간 설정할 필요 X
        } catch (NurigoMessageNotReceivedException exception) {
            // 발송에 실패한 메시지 목록을 확인
            log.error("exception.getFailedMessageList() : "+exception.getFailedMessageList());
            log.error("exception.getMessage() : "+exception.getMessage());
        } catch (Exception exception) {
            log.error("exception.getMessage() : "+exception.getMessage());
        }
    }

    private Message setMessage(String to){
        Message message = new Message();
        message.setFrom(senderPhone);
        message.setTo(to);
        message.setText("[moodbuddy] 쿼디의 고민 편지 답장이 도착했어요! 어서 확인해보세요 :)");

        return message;
    }
}
