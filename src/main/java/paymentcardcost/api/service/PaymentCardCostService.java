package paymentcardcost.api.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import paymentcardcost.api.models.dto.BinlistDto;
import paymentcardcost.api.models.dto.CardCostDto;
import paymentcardcost.api.models.domain.PaymentCardCost;
import paymentcardcost.api.repositories.PaymentCardCostRepository;

@Service
@Log4j2
public class PaymentCardCostService implements IPaymentCardCostService {

    private IBinlistClientService binlistClientService;
    private PaymentCardCostRepository paymentCardCostRepository;

    @Autowired
    public PaymentCardCostService(IBinlistClientService binlistClientService, PaymentCardCostRepository paymentCardCostRepository) {
        this.binlistClientService = binlistClientService;
        this.paymentCardCostRepository = paymentCardCostRepository;
    }

    @Override
    public CardCostDto CalculateCardCost(int cardNumber) {

        BinlistDto binDetails = this.binlistClientService.getBinMetadata(cardNumber);

        if (binDetails == null) return null;

        String country = binDetails.country.alpha2;

        CardCostDto cardCost = new CardCostDto();

        PaymentCardCost paymentCardCost = paymentCardCostRepository.findByCountry(country);

        if (paymentCardCost == null){
            log.info("Country not specified, cost set to $10.");
            cardCost.setCost(10.0);
            cardCost.setCountry("Other");

            return cardCost;
        }

        cardCost.setCountry(country);
        cardCost.setCost(paymentCardCost.cost);

        return cardCost;
    }
}
