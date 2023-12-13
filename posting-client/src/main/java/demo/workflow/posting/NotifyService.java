package demo.workflow.posting;

import demo.model.NotifyMessage;
import demo.model.RentRequestMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotifyService implements NotifyActivity {

    private final KafkaTemplate<String, RentRequestMessage> kafkaRentTemplate;

    private final KafkaTemplate<String, NotifyMessage> kafkaNotifyTemplate;

    public String sendMessage(RentRequestMessage msg) {
        log.info("Sent to bike service {} {} {} {} {} {}", msg.getRegion(), msg.getBikeQr(), msg.getHowLong(), msg.getMessage(), msg.getCommand(), msg.getWorkflowId());
        kafkaRentTemplate.send("rent-in", msg);
        return "OK";
    }

    @Override
    public String sendBike(RentRequestMessage message) {
        return sendMessage(message);
    }

    @Override
    public String sendNotify(NotifyMessage msg) {
        log.info("Sent notification {} {}", msg.getMessage(), msg.getAgent());
        kafkaNotifyTemplate.send("notify", msg);
        return "OK";
    }
}
