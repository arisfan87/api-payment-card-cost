package paymentcardcost.api.services;

import paymentcardcost.api.infrastructure.TooManyRequestsException;
import paymentcardcost.api.models.BinlistDto;

public interface IBinlistClientService {
    BinlistDto getBinMetadata(int bin);
}
