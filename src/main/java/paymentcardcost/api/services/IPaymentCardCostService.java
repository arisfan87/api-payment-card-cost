package paymentcardcost.api.services;

import paymentcardcost.api.infrastructure.BadRequestException;
import paymentcardcost.api.infrastructure.NotFoundException;
import paymentcardcost.api.infrastructure.TooManyRequestsException;
import paymentcardcost.api.models.dto.CardCostDto;

public interface IPaymentCardCostService {
    CardCostDto CalculateCardCost(int cardNumber) throws TooManyRequestsException, NotFoundException, BadRequestException;
}
