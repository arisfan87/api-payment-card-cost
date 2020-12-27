package paymentcardcost.api.controllers.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @JsonIgnore
    public boolean isCountryInputValid(){
        return this.country.toUpperCase(Locale.ROOT)
                .replaceAll("[^A-Z]", "")
                .replaceAll("(.)(?=.*\\1)", "")
                .length() == 2;
    }
}
