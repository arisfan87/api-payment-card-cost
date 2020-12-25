package paymentcardcost.api.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
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
    public void createClearingCost_save_runOnlyOnce() {

        // arrange
        PaymentCardCost pcc = new PaymentCardCost("GB", 18.0);

        // act
        clearingCostService.create(pcc);

        // assert
        Mockito.verify(paymentCardCostRepositoryMock, times(1)).save(pcc);
    }

    @Test
    public void deleteClearingCost_save_runOnlyOnce() {

        // arrange
        PaymentCardCost pcc = new PaymentCardCost("GB", 18.0);

        // act
        clearingCostService.delete(pcc);

        // assert
        Mockito.verify(paymentCardCostRepositoryMock, times(1)).delete(pcc);
    }

    @Test(expected = NotFoundException.class)
    public void updateClearingCost_throwsNotFound_run0Times() throws NotFoundException {

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
    public void updateClearingCost_save_runTimesOnce() throws NotFoundException {

        // arrange
        when(paymentCardCostRepositoryMock.findByCountry("GB")).thenReturn(new PaymentCardCost("GB", 16.0));
        PaymentCardCost pcc = new PaymentCardCost("GB", 18.0);

        // act
        clearingCostService.update(pcc);

        // assert
        Mockito.verify(paymentCardCostRepositoryMock, times(1)).save(pcc);
    }

    @Test
    public void findAllClearingCost_findAll_runTimesOnceAndNotEmpty() {

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
    public void ClearingCost_findByCountry_runTimesOnceAndNotEmpty() throws NotFoundException {

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

    @Test(expected = NotFoundException.class)
    public void ClearingCost_findByCountry_throwsNotFound() throws NotFoundException {

        // arrange
        when(paymentCardCostRepositoryMock
                .findByCountry("GR"))
                .thenReturn(null);

        // act
        PaymentCardCost sut = clearingCostService.findByCountry("GR");

        // assert
        assertThatExceptionOfType(NotFoundException.class);
        Mockito.verify(paymentCardCostRepositoryMock, times(0)).findByCountry(Mockito.anyString());
    }
}
