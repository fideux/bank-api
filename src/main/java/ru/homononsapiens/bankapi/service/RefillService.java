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

    public Long add(Refill refill) {
        return refillDao.save(refill);
    }

    public boolean confirm(Long id) {
        return refillDao.confirm(id);
    }
}