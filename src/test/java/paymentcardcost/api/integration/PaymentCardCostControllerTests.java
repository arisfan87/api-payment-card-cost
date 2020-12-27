package paymentcardcost.api.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import paymentcardcost.api.controllers.models.CardCostRequest;
import paymentcardcost.api.controllers.models.CardCostResponse;
import paymentcardcost.api.infrastructure.BadRequestException;
import paymentcardcost.api.infrastructure.NotFoundException;
import paymentcardcost.api.infrastructure.TooManyRequestsException;
import paymentcardcost.api.models.dto.BinlistDto;
import paymentcardcost.api.models.dto.CountryDto;
import paymentcardcost.api.services.IBinlistClientService;
import paymentcardcost.api.services.PaymentCardCostService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
@Sql({ "classpath:data.sql" })
public class PaymentCardCostControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Mock
    private IBinlistClientService binlistClientService;

    @InjectMocks
    private PaymentCardCostService paymentCardCostService;
    private static final String URL = "/api/payment-cards-cost";

    @Test
    public void calculateCardCost_alreadyConfiguredCountry_200OK() throws NotFoundException, TooManyRequestsException, BadRequestException {

        // arrange
        final double expectedUScardCost = 5.0;
        BinlistDto binlistDto = new BinlistDto();
        binlistDto.country = new CountryDto();
        binlistDto.country.alpha2 = "US";

        when(binlistClientService.getBinMetadata(42424242)).thenReturn(binlistDto);

        CardCostRequest cardCostRequest = new CardCostRequest();
        cardCostRequest.card_number = 42424242;

        // act
        ResponseEntity<CardCostResponse> response = restTemplate.postForEntity(URL, cardCostRequest, CardCostResponse.class);

        // assert
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertThat(response.getBody().getCost()).isEqualTo(expectedUScardCost);
    }

    @Test
    public void calculateCardCost_countryNotConfigured_200OK() throws NotFoundException, TooManyRequestsException, BadRequestException {

        // arrange
        final double expectedCardCostForNotConfiguredCountry = 10.0;
        BinlistDto binlistDto = new BinlistDto();
        binlistDto.country = new CountryDto();
        binlistDto.country.alpha2 = "ES";

        when(binlistClientService.getBinMetadata(42424242)).thenReturn(binlistDto);

        CardCostRequest cardCostRequest = new CardCostRequest();
        cardCostRequest.card_number = 40592100;

        // act
        ResponseEntity<CardCostResponse> response = restTemplate.postForEntity(URL, cardCostRequest, CardCostResponse.class);

        // assert
        assertEquals(HttpStatus.OK.value(), response.getStatusCodeValue());
        assertThat(response.getBody().getCost()).isEqualTo(expectedCardCostForNotConfiguredCountry);
    }
}
