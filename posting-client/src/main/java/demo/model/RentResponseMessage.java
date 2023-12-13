package demo.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentResponseMessage {
    public enum Type {AVAILABLE, NOT_AVAILABLE, OCCUPIED};

    private String message;
    private Type type;
    private String workflowId;
}
