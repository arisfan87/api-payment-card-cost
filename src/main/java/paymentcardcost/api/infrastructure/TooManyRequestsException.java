package paymentcardcost.api.infrastructure;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.TOO_MANY_REQUESTS, reason = "Request limit is set to 10 per minute.")
public class TooManyRequestsException extends Exception {

    public TooManyRequestsException()
    {
    }

    public TooManyRequestsException(String message)
    {
        super(message);
    }
}
