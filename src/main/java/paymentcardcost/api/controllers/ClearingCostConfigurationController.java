package paymentcardcost.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import paymentcardcost.api.controllers.models.ClearingCostConfigurationRequest;
import paymentcardcost.api.infrastructure.NotFoundException;
import paymentcardcost.api.models.domain.PaymentCardCost;
import paymentcardcost.api.unit.service.IClearingCostService;

import java.util.List;

@RestController
@RequestMapping("/api/cost-config")
public class ClearingCostConfigurationController {

    private IClearingCostService clearingCostService;

    @Autowired
    public ClearingCostConfigurationController(IClearingCostService clearingCostService) {
        this.clearingCostService = clearingCostService;
    }

    @RequestMapping(value = "list")
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentCardCost> list()
    {
        return this.clearingCostService.findAll();
    }

    @GetMapping(value = "{code}")
    @ResponseStatus(HttpStatus.OK)
    public PaymentCardCost getByCountry(@PathVariable String code) throws NotFoundException {
        return this.clearingCostService.findByCountry(code);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody final ClearingCostConfigurationRequest configuration) {
        PaymentCardCost cardCost = new PaymentCardCost(configuration.country, configuration.cost);
        this.clearingCostService.create(cardCost);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody final ClearingCostConfigurationRequest configuration) {
        PaymentCardCost cardCost = new PaymentCardCost(configuration.country, configuration.cost);
        this.clearingCostService.delete(cardCost);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody final ClearingCostConfigurationRequest configuration) throws NotFoundException
    {
        PaymentCardCost cardCost = new PaymentCardCost(configuration.country, configuration.cost);

        this.clearingCostService.update(cardCost);
    }
}
