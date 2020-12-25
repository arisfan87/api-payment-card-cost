package paymentcardcost.api.unit.service;

import paymentcardcost.api.models.dto.BinlistDto;

public interface IBinlistClientService {
    BinlistDto getBinMetadata(int bin);
}
