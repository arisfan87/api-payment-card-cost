package paymentcardcost.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import paymentcardcost.api.controllers.models.CardCostResponse;
import paymentcardcost.api.controllers.models.ClearingCostConfigurationRequest;
import paymentcardcost.api.infrastructure.CountryAlreadyExistException;
import paymentcardcost.api.infrastructure.NotFoundException;
import paymentcardcost.api.models.domain.PaymentCardCost;
import paymentcardcost.api.services.IClearingCostService;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/cost-config")
public class ClearingCostConfigurationController {

    private IClearingCostService clearingCostService;

    @Autowired
    public ClearingCostConfigurationController(IClearingCostService clearingCostService) {
        this.clearingCostService = clearingCostService;
    }

    @RequestMapping(value = "list")
    public ResponseEntity<List<CardCostResponse>> list()
    {
        List<PaymentCardCost> results = this.clearingCostService.findAll();

        if (results == null) return new ResponseEntity<List<CardCostResponse>>(HttpStatus.NOT_FOUND);

        List<CardCostResponse> cardCostsResponse = new ArrayList<CardCostResponse>();

        for (PaymentCardCost cardCost : results) {
            cardCostsResponse.add(new CardCostResponse(cardCost.country, cardCost.cost));
        }

        return new ResponseEntity<List<CardCostResponse>>(cardCostsResponse, HttpStatus.OK);
    }

    @GetMapping(value = "{code}")
    public ResponseEntity<CardCostResponse> getByCountry(@PathVariable String code)
    {
        PaymentCardCost result = this.clearingCostService.findByCountry(code.toUpperCase(Locale.ROOT));

        if (result == null) return new ResponseEntity<CardCostResponse>(HttpStatus.NOT_FOUND);

        CardCostResponse cardCostResponse = new CardCostResponse(result.country, result.cost);

        return new ResponseEntity<CardCostResponse>(cardCostResponse, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody final ClearingCostConfigurationRequest configuration) throws CountryAlreadyExistException {
        PaymentCardCost cardCost = new PaymentCardCost(configuration.country, configuration.cost);
        this.clearingCostService.create(cardCost);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @DeleteMapping()
    public ResponseEntity<Void> delete(@RequestBody final ClearingCostConfigurationRequest configuration) {
        PaymentCardCost cardCost = new PaymentCardCost(configuration.country, configuration.cost);
        this.clearingCostService.delete(cardCost);

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody final ClearingCostConfigurationRequest configuration) throws NotFoundException
    {
        PaymentCardCost cardCost = new PaymentCardCost(configuration.country, configuration.cost);

        this.clearingCostService.update(cardCost);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
