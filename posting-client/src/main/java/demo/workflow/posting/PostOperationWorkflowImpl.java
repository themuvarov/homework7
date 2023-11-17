package demo.workflow.posting;

import demo.model.Billing;

import demo.model.Counter;
import io.temporal.activity.Activity;
import io.temporal.api.workflowservice.v1.RegisterNamespaceRequest;
import io.temporal.workflow.Workflow;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Component;

import ai.applica.spring.boot.starter.temporal.annotations.ActivityStub;
import ai.applica.spring.boot.starter.temporal.annotations.RetryActivityOptions;
import ai.applica.spring.boot.starter.temporal.annotations.TemporalWorkflow;
import demo.model.PostingStatus;
import demo.model.Operation;
import io.temporal.failure.ActivityFailure;
import io.temporal.workflow.Saga;

@Component
@TemporalWorkflow("PostOperation")
public class PostOperationWorkflowImpl implements PostOperationWorkflow {

    @ActivityStub(scheduleToClose = "P1D",
            retryOptions = @RetryActivityOptions(doNotRetry = {"java.lang.NullPointerException"}, maximumAttempts = 1
                ))
    private PostingActivity postingActivity;

    private PostingStatus postingStatus = new PostingStatus();


    @Override
    public void process(Operation operation) {
        Saga.Options sagaOptions = new Saga.Options.Builder().build();
        Saga saga = new Saga(sagaOptions);

        try {
            Long incomingTimestamp = LocalDateTime.now().atZone(ZoneId.of("UTC")).toEpochSecond()*1000;

            Billing bill = new Billing(operation.getSum(), 232,
                    operation.getAgent(), incomingTimestamp);

            Integer refund = postingActivity.billing(bill);
            saga.addCompensation(postingActivity::rollbackBilling, bill);
            postingStatus.setRefund(refund);


            Integer outward = postingActivity.createOutward(bill);
            saga.addCompensation(postingActivity::rollbackOutward, bill);
            postingStatus.setCounter(outward);

            //Workflow.await(Duration.ofMinutes(100), () -> postingStatus.isCanceled());
            if(postingStatus.isCanceled()) {
                saga.compensate();
            }
        } catch (Exception e) {
            saga.compensate();
        }
    }

    @Override
    public void cancel() {
        postingStatus.setCanceled(true);
    }

    @Override
    public PostingStatus getPostingStatus() {
        return postingStatus;
    }
}