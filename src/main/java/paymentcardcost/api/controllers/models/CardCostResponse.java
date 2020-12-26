package paymentcardcost.api.controllers.models;

public class CardCostResponse {
    private String country;
    private double cost;

    public CardCostResponse(String country, double cost){
        this.country = country;
        this.cost = cost;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = this.country;
    }

    public double getCost() {
        return this.cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
