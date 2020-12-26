package paymentcardcost.api.unit.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import paymentcardcost.api.infrastructure.CountryAlreadyExistException;
import paymentcardcost.api.infrastructure.NotFoundException;
import paymentcardcost.api.models.domain.PaymentCardCost;
import paymentcardcost.api.repositories.PaymentCardCostRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class ClearingCostServiceTests {

    private PaymentCardCostRepository paymentCardCostRepositoryMock;
    private ClearingCostService clearingCostService;

    @Before
    public void setUp() {
        paymentCardCostRepositoryMock = Mockito.mock(PaymentCardCostRepository.class);
        clearingCostService = new ClearingCostService(paymentCardCostRepositoryMock);
    }

    @Test
    public void clearingCost_create_saveRunOnlyOnce() throws CountryAlreadyExistException {

        // arrange
        PaymentCardCost pcc = new PaymentCardCost("GB", 18.0);

        // act
        clearingCostService.create(pcc);

        // assert
        Mockito.verify(paymentCardCostRepositoryMock, times(1)).save(pcc);
    }

    @Test
    public void clearingCost_delete_deleteRunOnlyOnce() {

        // arrange
        PaymentCardCost pcc = new PaymentCardCost("GB", 18.0);

        // act
        clearingCostService.delete(pcc);

        // assert
        Mockito.verify(paymentCardCostRepositoryMock, times(1)).delete(pcc);
    }

    @Test(expected = NotFoundException.class)
    public void clearingCost_update_throwsNotFound() throws NotFoundException {

        // arrange
        when(paymentCardCostRepositoryMock.findByCountry("ES")).thenReturn(null);
        PaymentCardCost pcc = new PaymentCardCost("GB", 18.0);

        // act
        clearingCostService.update(pcc);

        // assert
        assertThatExceptionOfType(NotFoundException.class);
        Mockito.verify(paymentCardCostRepositoryMock, times(0)).save(pcc);
    }

    @Test
    public void clearingCost_update_saveRunTimesOnce() throws NotFoundException {

        // arrange
        when(paymentCardCostRepositoryMock.findByCountry("GB")).thenReturn(new PaymentCardCost("GB", 16.0));
        PaymentCardCost pcc = new PaymentCardCost("GB", 18.0);

        // act
        clearingCostService.update(pcc);

        // assert
        Mockito.verify(paymentCardCostRepositoryMock, times(1)).save(pcc);
    }

    @Test
    public void clearingCost_findAll_findAllRunTimesOnceAndNotEmpty() {

        // arrange
        List<PaymentCardCost> paymentCardCosts = new ArrayList<PaymentCardCost>();
        paymentCardCosts.add(new PaymentCardCost("GB", 18.0));
        paymentCardCosts.add(new PaymentCardCost("GR", 8.0));
        paymentCardCosts.add(new PaymentCardCost("US", 5.0));

        when(paymentCardCostRepositoryMock.findAll()).thenReturn(paymentCardCosts);

        // act
        List<PaymentCardCost> sut = clearingCostService.findAll();

        // assert
        assertThat(sut).isNotEmpty();
        assertThat(sut.size()).isEqualTo(paymentCardCosts.size());
        Mockito.verify(paymentCardCostRepositoryMock, times(1)).findAll();
    }

    @Test
    public void clearingCost_findByCountry_runTimesOnceAndNotEmpty() throws NotFoundException {

        // arrange
        when(paymentCardCostRepositoryMock
                .findByCountry("GR"))
                .thenReturn(new PaymentCardCost("GR", 8.0));

        // act
        PaymentCardCost sut = clearingCostService.findByCountry("GR");

        // assert
        assertThat(sut).isNotNull();
        assertThat(sut.getCost()).isEqualTo(8.0);
        Mockito.verify(paymentCardCostRepositoryMock, times(1)).findByCountry(Mockito.anyString());
    }

    @Test
    public void clearingCost_findByCountryIsNull_returnsNull() {

        // arrange
        when(paymentCardCostRepositoryMock
                .findByCountry("GR"))
                .thenReturn(null);

        // act
        PaymentCardCost sut = clearingCostService.findByCountry("GR");

        // assert
        assertThat(sut).isNull();
        Mockito.verify(paymentCardCostRepositoryMock, times(1)).findByCountry(Mockito.anyString());
    }
}
