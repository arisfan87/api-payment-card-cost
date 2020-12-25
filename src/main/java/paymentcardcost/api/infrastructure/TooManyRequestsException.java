package paymentcardcost.api.infrastructure;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Request limit is set to 10 per minute.")
public class TooManyRequestsException extends Exception {

    public TooManyRequestsException(String message)
    {
        super(message);
    }
}
