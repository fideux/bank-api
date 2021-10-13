package ru.homononsapiens.bankapi.dao.payment;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class PaymentService {

    private PaymentDao paymentDao;

    public List<Payment> getAll() {
        return paymentDao.findAll();
    }

    public void add(Payment payment) {
        payment.setStatus("waiting");
        paymentDao.save(payment);
    }

    public void confirm(Payment payment) {
        paymentDao.confirm(payment.getId());
    }
}