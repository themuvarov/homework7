package demo.workflow.posting;

import demo.model.*;

import java.math.BigDecimal;
import java.time.Duration;

import io.temporal.failure.CanceledFailure;
import io.temporal.workflow.Workflow;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import ai.applica.spring.boot.starter.temporal.annotations.ActivityStub;
import ai.applica.spring.boot.starter.temporal.annotations.RetryActivityOptions;
import ai.applica.spring.boot.starter.temporal.annotations.TemporalWorkflow;

import static demo.model.RentResponseMessage.Type.AVAILABLE;
import static demo.model.RentResponseMessage.Type.OCCUPIED;

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

    private enum BikeState {NONE, AVAILABLE, BUSY}

    private BikeState bikeState = BikeState.NONE;

    private enum Requested {NONE, CHECK, OCCUPY, RELEASE}

    private Requested requested = Requested.NONE;

    private static final Long PRICE_FOR_MINUTE = 10L;
    private static final long timeout = 2L;


    @Override
    public void process(RentingOperation rentingOperation) {
        long sum = rentingOperation.getPeriodMinutes() * PRICE_FOR_MINUTE;

        requestAvailability(rentingOperation.getRegion(), rentingOperation.getBikeQr());
        Workflow.await(Duration.ofMinutes(timeout),
                () -> requested == Requested.CHECK && (bikeState == BikeState.BUSY || bikeState == BikeState.AVAILABLE));
        if (bikeState == BikeState.AVAILABLE) {

            try {
                postingActivity.billing(new BillingDto(BigDecimal.valueOf(sum), rentingOperation.getAgent()));

                notifyActivity.sendNotify(new NotifyMessage(rentingOperation.getAgent(),
                        "Order has been created sum:" + sum + ", agent:" + sum));

                requestOccupy(rentingOperation.getRegion(), rentingOperation.getBikeQr(), rentingOperation.getPeriodMinutes());
                Workflow.await(Duration.ofMinutes(timeout),
                        () -> requested == Requested.OCCUPY && (bikeState == BikeState.BUSY || bikeState == BikeState.AVAILABLE));

                Workflow.sleep(Duration.ofMinutes(rentingOperation.getPeriodMinutes()));

                requestUnrent(rentingOperation.getRegion(), rentingOperation.getBikeQr());

            } catch (CanceledFailure e) {
                postingActivity.rollbackBilling(new BillingDto(BigDecimal.valueOf(sum), rentingOperation.getAgent()));
                notifyActivity.sendNotify(new NotifyMessage(rentingOperation.getAgent(),
                        "Bike already occupied, details - agent:" + rentingOperation.getAgent()));
            } catch (Exception e) {
                notifyActivity.sendNotify(new NotifyMessage(rentingOperation.getAgent(),
                        "Not enough money in account, details - sum:" + sum + ", agent:" + rentingOperation.getAgent()));
            }
        }
    }

    private void requestAvailability(Region region, String bikeQr) {
        RentRequestMessage message = new RentRequestMessage();
        message.setCommand(RentRequestMessage.Type.CHECK);
        message.setRegion(region);
        message.setBikeQr(bikeQr);
        message.setWorkflowId(Workflow.getInfo().getWorkflowId());
        requested = Requested.CHECK;
        bikeState = BikeState.NONE;
        notifyActivity.sendBike(message);
    }

    private void requestOccupy(Region region, String bikeQr, Long period) {
        // try to occupy the bike
        requested = Requested.OCCUPY;
        bikeState = BikeState.NONE;
        RentRequestMessage message = new RentRequestMessage();
        message = new RentRequestMessage();
        message.setWorkflowId(Workflow.getInfo().getWorkflowId());
        message.setCommand(RentRequestMessage.Type.RENT);
        message.setRegion(region);
        message.setBikeQr(bikeQr);
        message.setHowLong(period);
        notifyActivity.sendBike(message);
    }

    private void requestUnrent(Region region, String bikeQr) {
        // try to occupy the bike
        requested = Requested.OCCUPY;
        bikeState = BikeState.NONE;
        RentRequestMessage message = new RentRequestMessage();
        message = new RentRequestMessage();
        message.setWorkflowId(Workflow.getInfo().getWorkflowId());
        message.setCommand(RentRequestMessage.Type.UNRENT);
        message.setRegion(region);
        message.setBikeQr(bikeQr);
        notifyActivity.sendBike(message);
    }


    @Override
    public void response(RentResponseMessage message) {
        switch (requested) {
            case CHECK -> {
                if (message.getType() == AVAILABLE) {
                    bikeState = BikeState.AVAILABLE;
                } else {
                    bikeState = BikeState.BUSY;
                }
            }
            case OCCUPY -> {
                if (message.getType() == OCCUPIED) {
                    bikeState = BikeState.BUSY;
                } else {
                    bikeState = BikeState.AVAILABLE;
                }
            }
        }

    }


    @Override
    public PostingStatus getPostingStatus() {
        return postingStatus;
    }
}