package paymentcardcost.api.services;

import paymentcardcost.api.infrastructure.BadRequestException;
import paymentcardcost.api.infrastructure.NotFoundException;
import paymentcardcost.api.infrastructure.TooManyRequestsException;
import paymentcardcost.api.models.dto.BinlistDto;

public interface IBinlistClientService {
    BinlistDto getBinMetadata(int bin) throws NotFoundException, TooManyRequestsException, BadRequestException;
}
