package paymentcardcost.api.service;

import paymentcardcost.api.infrastructure.TooManyRequestsException;
import paymentcardcost.api.models.dto.CardCostDto;

public interface IPaymentCardCostService {
    CardCostDto CalculateCardCost(int cardNumber) throws TooManyRequestsException;
}
