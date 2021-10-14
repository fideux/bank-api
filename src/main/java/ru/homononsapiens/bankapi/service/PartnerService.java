package ru.homononsapiens.bankapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.homononsapiens.bankapi.model.Partner;
import ru.homononsapiens.bankapi.dao.PartnerDao;
import ru.homononsapiens.bankapi.utils.Util;
import java.util.List;

@Service
@AllArgsConstructor
public class PartnerService {

    private PartnerDao partnerDao;

    public List<Partner> getAll() {
        return partnerDao.getAll();
    }

    public List<Partner> getAllByClientId(Long clientId) {
        return partnerDao.findAllByClientId(clientId);
    }

    public Long add(Partner partner) {
        return partnerDao.save(partner);
    }
}
