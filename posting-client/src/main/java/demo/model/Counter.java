package demo.model;

import java.time.temporal.ChronoUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Counter {

    private Integer sum;
    private String counter;
    private String agent;
    private Long timestamp;
    private ChronoUnit chronoUnit;

}