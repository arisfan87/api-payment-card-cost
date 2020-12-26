package paymentcardcost.api.infrastructure;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Country already exists.")
public class CountryAlreadyExistException extends Exception {
    public CountryAlreadyExistException()
    {
    }

    public CountryAlreadyExistException(String message)
    {
        super(message);
    }
}
