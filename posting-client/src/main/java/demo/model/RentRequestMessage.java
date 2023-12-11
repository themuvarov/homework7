package demo.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentRequestMessage {
    public enum Type {RENT, UNRENT};

    private String message;
    private Type type;
    private String workflowId;
}
