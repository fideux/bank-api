package ru.homononsapiens.bankapi.dao.partner;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.homononsapiens.bankapi.dao.card.Card;
import ru.homononsapiens.bankapi.utils.Util;
import java.util.List;

@Service
@AllArgsConstructor
public class PartnerService {

    private PartnerDao partnerDao;

    public List<Partner> getAll() {
        return partnerDao.findAll();
    }

    public List<Partner> getAllByClientId(Long clientId) {
        return partnerDao.findAllByClientId(clientId);
    }

    public JsonNode add(Partner partner) {
        if (partnerDao.save(partner)) {
            return Util.getMessageAsJsonObject("OK", "Контрагент успешно создан");
        }
        return Util.getMessageAsJsonObject("Error", "Ошибка при создании контрагента");
    }
}
