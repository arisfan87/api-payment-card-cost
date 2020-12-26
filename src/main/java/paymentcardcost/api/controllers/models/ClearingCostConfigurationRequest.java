package paymentcardcost.api.controllers.models;

import com.sun.istack.NotNull;

import java.util.Locale;

public class ClearingCostConfigurationRequest {

    @NotNull
    public String country;

    public String getCountry() {
        return country.toUpperCase(Locale.ROOT);
    }

    public void setCountry(String country) {
        this.country = country.toUpperCase(Locale.ROOT);
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double cost;
}
