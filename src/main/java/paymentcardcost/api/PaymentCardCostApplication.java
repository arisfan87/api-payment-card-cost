package paymentcardcost.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import paymentcardcost.api.repositories.PaymentCardCostRepository;

@SpringBootApplication()
public class PaymentCardCostApplication {

    private final PaymentCardCostRepository paymentCardCostRepository;

    public PaymentCardCostApplication(PaymentCardCostRepository paymentCardCostRepository) {
        this.paymentCardCostRepository = paymentCardCostRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(PaymentCardCostApplication.class, args);
    }

}
