package paymentcardcost.api.service;

import paymentcardcost.api.models.dto.BinlistDto;

public interface IBinlistClientService {
    BinlistDto getBinMetadata(int bin);
}
