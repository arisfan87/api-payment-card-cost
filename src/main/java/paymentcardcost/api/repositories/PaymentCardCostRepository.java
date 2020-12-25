package paymentcardcost.api.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import paymentcardcost.api.models.domain.PaymentCardCost;

@Repository
public interface PaymentCardCostRepository extends CrudRepository<PaymentCardCost, String> {
    PaymentCardCost findByCountry(String country);
}
