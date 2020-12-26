package paymentcardcost.api.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import paymentcardcost.api.controllers.models.CardCostResponse;
import paymentcardcost.api.controllers.models.ClearingCostConfigurationRequest;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:test.properties")
@Sql({ "classpath:data.sql" })
public class ClearingCostConfigurationControllerTests {

    @Autowired
    private TestRestTemplate restTemplate;

    private static final String URL = "/api/cost-config";

    @Test
    public void clearingCost_create_201Created() {

        // arrange
        ClearingCostConfigurationRequest configuration = new ClearingCostConfigurationRequest();
        configuration.cost = 2.0;
        configuration.country = "ES";

        // act
        ResponseEntity<Void> response = restTemplate.postForEntity(URL, configuration, void.class);

        // assert
        assertEquals(HttpStatus.CREATED.value(), response.getStatusCodeValue());
    }

    @Test
    public void clearingCost_createCountryAlreadyConfigured_409Conflict() {

        // arrange
        ClearingCostConfigurationRequest configuration = new ClearingCostConfigurationRequest();
        configuration.cost = 2.0;
        configuration.country = "GR";

        // act
        ResponseEntity<Void> response = restTemplate.postForEntity(URL, configuration, void.class);

        // assert
        assertEquals(HttpStatus.CONFLICT.value(), response.getStatusCodeValue());
    }

    @Test
    public void clearingCost_getByCountryNotConfigured_404NotFound() {

        // arrange
        final String URL = "/api/cost-config/GB";

        // act
        ResponseEntity<CardCostResponse> response = restTemplate.getForEntity(URL, CardCostResponse.class);
        int status = response.getStatusCodeValue();

        // assert
        assertEquals(HttpStatus.NOT_FOUND.value(), status);
    }

    @Test
    public void clearingCost_getByCountryConfigured_200OK() {

        // arrange
        final String URL = "/api/cost-config/GR";

        // act
        ResponseEntity<CardCostResponse> response = restTemplate.getForEntity(URL, CardCostResponse.class);
        int status = response.getStatusCodeValue();

        // assert
        assertEquals(HttpStatus.OK.value(), status);
    }

    @Test
    public void clearingCost_delete_204NoContent() {

        // arrange
        ClearingCostConfigurationRequest configuration = new ClearingCostConfigurationRequest();
        HttpEntity<ClearingCostConfigurationRequest> requestEntity = new HttpEntity<ClearingCostConfigurationRequest>(configuration);
        configuration.country = "US";

        // act
        ResponseEntity<Void> response = restTemplate.exchange(URL, HttpMethod.DELETE, requestEntity, Void.class);
        int status = response.getStatusCodeValue();

        // assert
        assertEquals(HttpStatus.NO_CONTENT.value(), status);
    }

    @Test
    public void clearingCost_update_201Created() {

        // arrange
        ClearingCostConfigurationRequest configuration = new ClearingCostConfigurationRequest();
        HttpEntity<ClearingCostConfigurationRequest> requestEntity = new HttpEntity<ClearingCostConfigurationRequest>(configuration);
        configuration.country = "US";
        configuration.cost = 22.0;

        // act
        ResponseEntity<Void> response = restTemplate.exchange(URL, HttpMethod.PUT, requestEntity, Void.class);
        int status = response.getStatusCodeValue();

        // assert
        assertEquals(HttpStatus.CREATED.value(), status);
    }

    @Test
    public void clearingCost_updateCountryNotConfigured_404NotFound() {

        // arrange
        ClearingCostConfigurationRequest configuration = new ClearingCostConfigurationRequest();
        HttpEntity<ClearingCostConfigurationRequest> requestEntity = new HttpEntity<ClearingCostConfigurationRequest>(configuration);
        configuration.country = "ES";
        configuration.cost = 15.0;

        // act
        ResponseEntity<Void> response = restTemplate.exchange(URL, HttpMethod.PUT, requestEntity, Void.class);
        int status = response.getStatusCodeValue();

        // assert
        assertEquals(HttpStatus.NOT_FOUND.value(), status);
    }
}
