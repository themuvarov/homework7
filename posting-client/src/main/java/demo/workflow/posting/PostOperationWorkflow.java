package demo.workflow.posting;

import ai.applica.spring.boot.starter.temporal.annotations.TemporalWorkflow;
import demo.model.PostingStatus;
import demo.model.RentResponseMessage;
import demo.model.RentingOperation;
import io.temporal.workflow.QueryMethod;
import io.temporal.workflow.SignalMethod;
import io.temporal.workflow.WorkflowInterface;
import io.temporal.workflow.WorkflowMethod;

@WorkflowInterface
@TemporalWorkflow("PostOperation")
public interface PostOperationWorkflow {

    @WorkflowMethod
    void process(RentingOperation rentingOperation);

    @SignalMethod
    void response(RentResponseMessage message);

    @QueryMethod
    PostingStatus getPostingStatus();


}
