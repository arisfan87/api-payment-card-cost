package paymentcardcost.api.models.domain;

import javax.persistence.*;

@Entity
@Table(name = "CardCost")
public class PaymentCardCost {

    protected PaymentCardCost() {}

    public PaymentCardCost(String country, Double cost) {
        this.country = country;
        this.cost = cost;
    }

    @Id
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
