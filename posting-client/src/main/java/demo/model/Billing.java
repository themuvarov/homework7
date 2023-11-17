package demo.model;

import java.time.LocalDateTime;
import java.time.ZoneId;

public class Billing {

    private Integer sum;
    private Integer tariff;
    private String agent;
    private Long timestamp;

    public Billing(Integer sum, Integer tariff, String agent, Long timestamp) {
        this.sum = sum;
        this.tariff = tariff;
        this.agent = agent;
        this.timestamp = timestamp;
    }

    public Billing() {

    }

    public Integer getSum() {
        return sum;
    }

    public void setSum(Integer sum) {
        this.sum = sum;
    }

    public Integer getTariff() {
        return tariff;
    }

    public void setTariff(Integer tariff) {
        this.tariff = tariff;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}