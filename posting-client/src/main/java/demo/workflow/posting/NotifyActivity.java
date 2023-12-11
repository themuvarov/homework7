package demo.workflow.posting;

import demo.model.NotifyMessage;
import demo.model.RentRequestMessage;
import io.temporal.activity.ActivityInterface;

@ActivityInterface
public interface NotifyActivity {
    String sendBike(RentRequestMessage message);
    String sendNotify(NotifyMessage message);


}
