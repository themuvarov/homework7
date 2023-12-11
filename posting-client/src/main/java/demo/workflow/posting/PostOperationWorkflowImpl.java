package demo.workflow.posting;

import demo.model.*;

import io.temporal.workflow.Workflow;

import java.math.BigDecimal;
import java.time.Duration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import ai.applica.spring.boot.starter.temporal.annotations.ActivityStub;
import ai.applica.spring.boot.starter.temporal.annotations.RetryActivityOptions;
import ai.applica.spring.boot.starter.temporal.annotations.TemporalWorkflow;

@Component
@TemporalWorkflow("PostOperation")
@Slf4j
public class PostOperationWorkflowImpl implements PostOperationWorkflow {

    @ActivityStub(scheduleToClose = "P1D",
            retryOptions = @RetryActivityOptions(doNotRetry = {"java.lang.NullPointerException", "demo.workflow.posting.PostingException"}, maximumAttempts = 1
                ))
    private PostingActivity postingActivity;

    @ActivityStub(scheduleToClose = "P1D",
            retryOptions = @RetryActivityOptions(doNotRetry = {"java.lang.NullPointerException", "demo.workflow.posting.PostingException"}, maximumAttempts = 1
            ))
    private NotifyActivity notifyActivity;

    private PostingStatus postingStatus = new PostingStatus();


    @Override
    public void process(Operation operation) {

        try {
            BillingDto bill = new BillingDto(BigDecimal.valueOf(operation.getSum()), operation.getAgent());
            postingActivity.billing(bill);

            NotifyMessage message = new NotifyMessage(operation.getAgent(), "Order has been created sum:" + operation.getSum() + ", agent:" + operation.getSum());
            notifyActivity.sendNotify(message);
        } catch (Exception e) {
            NotifyMessage message = new NotifyMessage(operation.getAgent(), "Not enough money in account, details - sum:" + operation.getSum() + ", agent:" + operation.getSum());
            notifyActivity.sendNotify(message);
        }



        /*
        Workflow.getInfo().getWorkflowId();
        RentRequestMessage message = new RentRequestMessage();
        message.setWorkflowId(Workflow.getInfo().getWorkflowId());
        message.setMessage("notification to myself");
        message.setType(RentRequestMessage.Type.RENT);
        notifyActivity.sendBike(message);


        Workflow.await(Duration.ofMinutes(100), () -> postingStatus.isCanceled());
        if(postingStatus.isCanceled()) {
           log.info("Proceed with cancel status!!!!");
        }

         */

        /*Saga.Options sagaOptions = new Saga.Options.Builder().build();
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

         */
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