package paymentcardcost.api.services;

import paymentcardcost.api.models.dto.BinlistDto;

public interface IBinlistClientService {
    BinlistDto getBinMetadata(int bin);
}
