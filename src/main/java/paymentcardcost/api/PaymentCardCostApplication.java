package paymentcardcost.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import paymentcardcost.api.repositories.PaymentCardCostRepository;

import javax.annotation.PostConstruct;

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
