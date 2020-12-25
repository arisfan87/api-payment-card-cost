package paymentcardcost.api.services;

import paymentcardcost.api.infrastructure.TooManyRequestsException;
import paymentcardcost.api.models.CardCostDto;

public interface IPaymentCardCostService {
    CardCostDto CalculateCardCost(int cardNumber) throws TooManyRequestsException;
}
