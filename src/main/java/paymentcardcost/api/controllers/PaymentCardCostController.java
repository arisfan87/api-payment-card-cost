package paymentcardcost.api.controllers;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import paymentcardcost.api.controllers.models.CardCostRequest;
import paymentcardcost.api.controllers.models.CardCostResponse;
import paymentcardcost.api.infrastructure.BadRequestException;
import paymentcardcost.api.infrastructure.NotFoundException;
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
    @ApiResponses(value = {
            @ApiResponse(code = 400, message = "Invalid request model."),
            @ApiResponse(code = 404, message = "Resource not found."),
            @ApiResponse(code = 429, message = "Too many requests.")
    })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CardCostResponse> CalculateCardCost(@RequestBody CardCostRequest cardCost) throws TooManyRequestsException, NotFoundException, BadRequestException {

        CardCostDto result = this.paymentCardCostService.CalculateCardCost(cardCost.card_number);

        CardCostResponse response = new CardCostResponse(result.country, result.cost);

        return new ResponseEntity<CardCostResponse>(response, HttpStatus.OK);
    }
}
