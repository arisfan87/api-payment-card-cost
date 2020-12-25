package paymentcardcost.api.unit.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import paymentcardcost.api.infrastructure.RestTemplateResponseErrorHandler;
import paymentcardcost.api.models.dto.BinlistDto;

@Service
@Log4j2
public class BinlistClientService implements IBinlistClientService {
    private final RestTemplate restTemplate;

    public BinlistClientService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public BinlistDto getBinMetadata(int bin) {
        final String url = String.format("https://lookup.binlist.net/%s", bin);

        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        ResponseEntity<BinlistDto> response = this.restTemplate.getForEntity(url, BinlistDto.class);

        if (response.getStatusCode() == HttpStatus.OK)
        {
            log.info("Binlist OK.");
        }

        return response.getBody();
    }
}
