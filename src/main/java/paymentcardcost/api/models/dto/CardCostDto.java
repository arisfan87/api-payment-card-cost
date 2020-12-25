package paymentcardcost.api.models.dto;

public class CardCostDto {
    public String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        country = country;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        cost = cost;
    }

    public Double cost;
}
