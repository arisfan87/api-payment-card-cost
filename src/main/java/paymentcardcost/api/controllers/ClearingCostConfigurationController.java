package paymentcardcost.api.controllers;

import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 200, message = "Configured card cost."),
    })
    @ResponseStatus(HttpStatus.OK)
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

    @RequestMapping(value = "{code}", method = RequestMethod.GET)
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 200, message = "Configured card cost."),
    })
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CardCostResponse> getByCountry(@PathVariable String code)
    {
        PaymentCardCost result = this.clearingCostService.findByCountry(code.toUpperCase(Locale.ROOT));

        if (result == null) return new ResponseEntity<CardCostResponse>(HttpStatus.NOT_FOUND);

        CardCostResponse cardCostResponse = new CardCostResponse(result.country, result.cost);

        return new ResponseEntity<CardCostResponse>(cardCostResponse, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New country cost configured."),
            @ApiResponse(code = 400, message = "Invalid request model."),
            @ApiResponse(code = 409, message = "Country already configured.")
    })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> create(@RequestBody final ClearingCostConfigurationRequest configuration) throws CountryAlreadyExistException {

        if (!configuration.isCountryInputValid()) return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);

        PaymentCardCost cardCost = new PaymentCardCost(configuration.country, configuration.cost);
        this.clearingCostService.create(cardCost);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> delete(@RequestBody final ClearingCostConfigurationRequest configuration) {
        PaymentCardCost cardCost = new PaymentCardCost(configuration.country, configuration.cost);
        this.clearingCostService.delete(cardCost);

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.PUT)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Update completed."),
            @ApiResponse(code = 404, message = "Resource not found."),
    })
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> update(@RequestBody final ClearingCostConfigurationRequest configuration) throws NotFoundException
    {
        PaymentCardCost cardCost = new PaymentCardCost(configuration.country, configuration.cost);

        this.clearingCostService.update(cardCost);

        return new ResponseEntity<Void>(HttpStatus.CREATED);
    }
}
