package ru.homononsapiens.bankapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.homononsapiens.bankapi.model.Refill;
import ru.homononsapiens.bankapi.dao.RefillDao;
import ru.homononsapiens.bankapi.utils.Util;

import java.util.List;

@Service
@AllArgsConstructor
public class RefillService {

    private RefillDao refillDao;

    public List<Refill> getAll() {
        return refillDao.getAll();
    }

    public JsonNode add(Refill refill) {
        refill.setStatus("waiting");
        if (refillDao.save(refill)) {
            return Util.getMessageAsJsonObject("OK", "Запрос на пополнение успешно создан. Ожидайте подтверждения");
        }
        return Util.getMessageAsJsonObject("Error", "Ошибка при создании запроса на пополнение");
    }

    public JsonNode confirm(Refill refill) {
        if (refillDao.confirm(refill.getId())) {
            return Util.getMessageAsJsonObject("OK", "Запрос на пополнение успешно подтвержден");
        }
        return Util.getMessageAsJsonObject("Error", "Ошибка при подтверждении запроса на пополнение");
    }
}