package paymentcardcost.api.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import paymentcardcost.api.models.domain.PaymentCardCost;
import paymentcardcost.api.models.dto.BinlistDto;
import paymentcardcost.api.models.dto.CardCostDto;
import paymentcardcost.api.models.dto.CountryDto;
import paymentcardcost.api.repositories.PaymentCardCostRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PaymentCardCostServiceTests {

    private IBinlistClientService binlistClientService;
    private PaymentCardCostRepository paymentCardCostRepository;
    private PaymentCardCostService paymentCardCostService;

    @Before
    public void setUp() {
        binlistClientService = Mockito.mock(IBinlistClientService.class);
        paymentCardCostRepository = Mockito.mock(PaymentCardCostRepository.class);

        paymentCardCostService = new PaymentCardCostService(binlistClientService, paymentCardCostRepository);
    }

    @Test
    public void calculateCardCost_alpha2IsUS_equalTo10(){

        // arrange
        final int bin = 42424242;
        BinlistDto binlist = new BinlistDto();
        binlist.country = new CountryDto();
        binlist.country.alpha2 = "US";

        when(this.binlistClientService.getBinMetadata(bin)).thenReturn(binlist);
        when(this.paymentCardCostRepository.findByCountry(Mockito.eq("US")))
                .thenReturn(new PaymentCardCost("US", 10.0));

        // act
        CardCostDto sut = this.paymentCardCostService.CalculateCardCost(bin);

        // assert
        assertThat(sut.getCost()).isEqualTo(10.0);
        Mockito.verify(binlistClientService, times(1)).getBinMetadata(Mockito.anyInt());
        Mockito.verify(paymentCardCostRepository, times(1)).findByCountry(Mockito.anyString());
    }

    @Test
    public void calculateCardCost_countryNotConfigured_costEqualToDefault(){

        // arrange
        final int bin = 42424242;
        final String expectedCountryOther = "Other";
        BinlistDto binlist = new BinlistDto();
        binlist.country = new CountryDto();
        binlist.country.alpha2 = "US";

        when(this.binlistClientService.getBinMetadata(bin)).thenReturn(binlist);
        when(this.paymentCardCostRepository.findByCountry(Mockito.eq("US")))
                .thenReturn(null);

        // act
        CardCostDto sut = this.paymentCardCostService.CalculateCardCost(bin);

        // assert
        assertThat(sut.getCost()).isEqualTo(10.0);
        assertThat(sut.getCountry()).isEqualTo(expectedCountryOther);

        Mockito.verify(binlistClientService, times(1)).getBinMetadata(Mockito.anyInt());
        Mockito.verify(paymentCardCostRepository, times(1)).findByCountry(Mockito.anyString());
    }
}
