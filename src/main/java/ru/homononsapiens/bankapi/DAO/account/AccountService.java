package ru.homononsapiens.bankapi.DAO.account;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class AccountService {
    private AccountDAO accountDAO;

    public Account find(Long id) {
        return accountDAO.findById(id);
    }

    public List<Account> findAll() {
        return accountDAO.findAll();
    }

    public void put(Account account) {
        accountDAO.put(account);
    }
}
