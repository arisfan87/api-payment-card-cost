package paymentcardcost.api.controllers;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import paymentcardcost.api.controllers.models.CardCostRequest;
import paymentcardcost.api.controllers.models.CardCostResponse;
import paymentcardcost.api.infrastructure.TooManyRequestsException;
import paymentcardcost.api.models.dto.CardCostDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import paymentcardcost.api.services.IPaymentCardCostService;

@RestController()
@RequestMapping("/api")
@Log4j2
public class PaymentCardCostController {

    private IPaymentCardCostService paymentCardCostService;

    @Autowired
    public PaymentCardCostController(IPaymentCardCostService paymentCardCostService) {
        this.paymentCardCostService = paymentCardCostService;
    }

    @RequestMapping(value = "payment-cards-cost", method = RequestMethod.POST)
    public ResponseEntity<CardCostResponse> CalculateCardCost(@RequestBody CardCostRequest card_number) throws TooManyRequestsException {

        CardCostDto result = this.paymentCardCostService.CalculateCardCost(card_number.card_number);

        CardCostResponse response = new CardCostResponse(result.country, result.cost);

        return new ResponseEntity<CardCostResponse>(response, HttpStatus.OK);
    }
}
