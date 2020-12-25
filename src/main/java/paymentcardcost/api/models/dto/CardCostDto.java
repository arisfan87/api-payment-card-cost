package paymentcardcost.api.models.dto;

public class CardCostDto {
    public String country;

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Double getCost() {
        return this.cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double cost;
}
