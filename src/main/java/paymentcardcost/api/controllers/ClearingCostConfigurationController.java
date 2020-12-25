package paymentcardcost.api.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import paymentcardcost.api.infrastructure.NotFoundException;
import paymentcardcost.api.models.PaymentCardCost;
import paymentcardcost.api.services.IClearingCostService;

import java.util.List;

@RestController
@RequestMapping("api")
public class ClearingCostConfigurationController {

    private IClearingCostService clearingCostService;

    @Autowired
    public ClearingCostConfigurationController(IClearingCostService clearingCostService) {
        this.clearingCostService = clearingCostService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<PaymentCardCost> list(){
        return this.clearingCostService.findAll();
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PaymentCardCost getById(@PathVariable String code) throws NotFoundException {
        return this.clearingCostService.getByCountry(code);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody final PaymentCardCost paymentCardCost){
        this.clearingCostService.create(paymentCardCost);
    }

    @DeleteMapping()
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestBody final PaymentCardCost paymentCardCost){
        this.clearingCostService.delete(paymentCardCost);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody final PaymentCardCost paymentCardCost) throws NotFoundException {
        this.clearingCostService.update(paymentCardCost);
    }
}
