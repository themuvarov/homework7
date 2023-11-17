package demo.model;

public class Outward {
    private String agent;
    private Integer tariff;

    public Outward(String agent, Integer tariff) {
        this.agent = agent;
        this.tariff = tariff;
    }

    public Outward() {}

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
}
