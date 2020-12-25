package paymentcardcost.api.unit.service;

import paymentcardcost.api.infrastructure.NotFoundException;
import paymentcardcost.api.models.domain.PaymentCardCost;

import java.util.List;

public interface IClearingCostService {
    void create(PaymentCardCost paymentCardCost);
    void delete(PaymentCardCost paymentCardCost);
    void update(PaymentCardCost paymentCardCost) throws NotFoundException;
    List<PaymentCardCost> findAll();
    PaymentCardCost findByCountry(String country) throws NotFoundException;
}
