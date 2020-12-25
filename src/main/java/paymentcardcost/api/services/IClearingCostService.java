package paymentcardcost.api.services;

import paymentcardcost.api.infrastructure.NotFoundException;
import paymentcardcost.api.models.PaymentCardCost;

import java.util.List;

public interface IClearingCostService {
    void create(PaymentCardCost paymentCardCost);
    void delete(PaymentCardCost paymentCardCost);
    void update(PaymentCardCost paymentCardCost) throws NotFoundException;
    List<PaymentCardCost> findAll();
    PaymentCardCost getByCountry(String country) throws NotFoundException;
}
