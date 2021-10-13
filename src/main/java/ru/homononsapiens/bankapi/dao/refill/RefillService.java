package ru.homononsapiens.bankapi.dao.refill;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RefillService {

    private RefillDao refillDao;

    public List<Refill> getAll() {
        return refillDao.findAll();
    }

    public void add(Refill refill) {
        refill.setStatus("waiting");
        refillDao.save(refill);
    }

    public void confirm(Refill refill) {
        refillDao.confirm(refill.getId());
    }
}