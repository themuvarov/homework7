package demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentingOperation {
    private String id;
    private String agent;
    private Long periodMinutes;
    private Region region;
    private String bikeQr;
}
