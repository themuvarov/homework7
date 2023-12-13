package demo.workflow.posting;

import demo.model.RentRequestMessage;
import demo.model.RentResponseMessage;
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

    @KafkaListener(topics = "rent-out", groupId = "orchestrator", containerFactory = "kafkaListenerContainerFactory")
    public void listenGroupFoo(RentResponseMessage message) {
        log.info("Received Message in group rent1: {} {} {}", message.getMessage(), message.getType(), message.getWorkflowId());
        workflowClient.newWorkflowStub(PostOperationWorkflow.class, message.getWorkflowId()).response(message);
    }
}
