package paymentcardcost.api.controllers.models;

import com.sun.istack.NotNull;

public class ClearingCostConfigurationRequest {

    @NotNull
    public String country;

    public Double cost;
}
