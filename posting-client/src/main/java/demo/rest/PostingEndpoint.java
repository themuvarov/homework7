package demo.rest;

import demo.model.PostingStatus;
import java.util.UUID;

import demo.model.RentingOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ai.applica.spring.boot.starter.temporal.WorkflowFactory;
import demo.workflow.posting.PostOperationWorkflow;
import io.temporal.api.common.v1.WorkflowExecution;
import io.temporal.client.WorkflowClient;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/post/v1")
public class PostingEndpoint {

    private static final Logger LOG = LogManager.getLogger();

    @Autowired
    private WorkflowFactory fact;
    @Autowired
    private WorkflowClient workflowClient;

    @PostMapping(value = "/rent",
            consumes = APPLICATION_JSON_VALUE,
            produces = APPLICATION_JSON_VALUE)
    ResponseEntity<String> rentBike(@RequestBody RentingOperation rentingOperation) {
        String id = "rent-"+UUID.randomUUID().toString();
        PostOperationWorkflow workflow =fact.makeStub(PostOperationWorkflow.class,
                fact.defaultOptionsBuilder(PostOperationWorkflow.class)
                        .setWorkflowId(id));
        WorkflowExecution execution = WorkflowClient.start(workflow::process, rentingOperation);

        return ResponseEntity.ok(id);
    }
/*
    @PutMapping(value = "/operation/{num}/cancel")
    ResponseEntity<Void> cancelBilling(@PathVariable String num) {
        workflowClient.newWorkflowStub(PostOperationWorkflow.class, num).cancel();
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/operation/{num}")
    ResponseEntity<PostingStatus> status(@PathVariable String num) {
        PostingStatus postingStatus =
                workflowClient.newWorkflowStub(PostOperationWorkflow.class, num).getPostingStatus();
        return ResponseEntity.ok(postingStatus);
    }

 */
}
