package demo.workflow.posting;

import demo.model.BillingDto;
import demo.model.Counter;
import demo.model.Billing;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface PostingActivity {

    Integer billing(BillingDto billing);

    Integer rollbackBilling(BillingDto billing);


}
