package paymentcardcost.api.services;

import lombok.extern.log4j.Log4j2;
import lombok.val;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.*;
import paymentcardcost.api.infrastructure.BadRequestException;
import paymentcardcost.api.infrastructure.NotFoundException;
import paymentcardcost.api.infrastructure.RestTemplateResponseErrorHandler;
import paymentcardcost.api.infrastructure.TooManyRequestsException;
import paymentcardcost.api.models.dto.BinlistDto;
import paymentcardcost.api.models.dto.CountryDto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Log4j2
public class BinlistClientService implements IBinlistClientService {
    private final RestTemplate restTemplate;

    public BinlistClientService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .errorHandler(new RestTemplateResponseErrorHandler())
                .build();
    }

    public BinlistDto getBinMetadata(int bin) {
        final String url = String.format("https://lookup.binlist.net/%s", bin);

        this.restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
        ResponseEntity<BinlistDto> response = this.restTemplate.getForEntity(url, BinlistDto.class);

        return response.getBody();
    }
}
