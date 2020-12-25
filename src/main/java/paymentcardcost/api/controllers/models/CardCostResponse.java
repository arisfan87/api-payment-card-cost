package paymentcardcost.api.controllers.models;

public class CardCostResponse {
    private String Country;
    private double Cost;

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public double getCost() {
        return Cost;
    }

    public void setCost(double cost) {
        Cost = cost;
    }
}
