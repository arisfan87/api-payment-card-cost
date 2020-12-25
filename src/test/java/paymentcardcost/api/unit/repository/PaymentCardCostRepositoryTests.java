package paymentcardcost.api.unit.repository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;
import paymentcardcost.api.models.domain.PaymentCardCost;
import paymentcardcost.api.repositories.PaymentCardCostRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PaymentCardCostRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private PaymentCardCostRepository paymentCardCostRepository;

    @Test
    public void findByCountry_countryExists_returnCost() {

        // arrange
        PaymentCardCost card = new PaymentCardCost("GB", 3.0);
        entityManager.persist(card);
        entityManager.flush();

        // act
        PaymentCardCost sut = paymentCardCostRepository.findByCountry("GB");

        // assert
        assertThat(sut.getCost()).isEqualTo(card.getCost());
    }
}
