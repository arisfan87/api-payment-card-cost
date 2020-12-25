package paymentcardcost.api.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import paymentcardcost.api.infrastructure.NotFoundException;
import paymentcardcost.api.models.domain.PaymentCardCost;
import paymentcardcost.api.repositories.PaymentCardCostRepository;

import java.util.List;

@Service
@Log4j2
public class ClearingCostService implements IClearingCostService{

    private PaymentCardCostRepository paymentCardCostRepository;

    public ClearingCostService(PaymentCardCostRepository paymentCardCostRepository) {
        this.paymentCardCostRepository = paymentCardCostRepository;
    }

    @Override
    public void create(PaymentCardCost paymentCardCost)
    {
        this.paymentCardCostRepository.save(paymentCardCost);
    }

    @Override
    public void delete(PaymentCardCost paymentCardCost) {

        this.paymentCardCostRepository.delete(paymentCardCost);
    }

    @Override
    public void update(PaymentCardCost paymentCardCost) throws NotFoundException {
        PaymentCardCost cardCost = this.paymentCardCostRepository.findByCountry(paymentCardCost.country);

        if (cardCost == null) {
            log.warn("Cost not found for country {country}", paymentCardCost.country);
            throw new NotFoundException();
        }

        this.paymentCardCostRepository.save(paymentCardCost);
    }

    @Override
    public List<PaymentCardCost> findAll() {
        return (List<PaymentCardCost>) this.paymentCardCostRepository.findAll();
    }

    @Override
    public PaymentCardCost findByCountry(String country) throws NotFoundException {
        PaymentCardCost cardCost = this.paymentCardCostRepository.findByCountry(country);

        if (cardCost == null) {
            log.warn("Cost not found for country {country}", country);
            throw new NotFoundException();
        }

        return cardCost;
    }
}
