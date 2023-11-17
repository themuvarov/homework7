package demo.workflow.posting;

import demo.model.Counter;
import demo.model.Billing;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface PostingActivity {

    Integer billing(Billing billing);

    Integer rollbackBilling(Billing billing);

    Integer createOutward(Billing billing);
    Integer rollbackOutward(Billing billing);

}
