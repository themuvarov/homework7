package demo.workflow.posting;

import demo.model.RentRequestMessage;
import io.temporal.client.WorkflowClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class KafkaConsumer {

    @Autowired
    private WorkflowClient workflowClient;

    @KafkaListener(topics = "rent", groupId = "rent1", containerFactory = "kafkaListenerContainerFactory")
    public void listenGroupFoo(RentRequestMessage message) {
        log.info("Received Message in group rent1: {} {} {}", message.getMessage(), message.getType(), message.getWorkflowId());
        workflowClient.newWorkflowStub(PostOperationWorkflow.class, message.getWorkflowId()).cancel();
    }
}
