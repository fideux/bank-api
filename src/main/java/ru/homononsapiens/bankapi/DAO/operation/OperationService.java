package ru.homononsapiens.bankapi.DAO.operation;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.homononsapiens.bankapi.DAO.account.Account;
import ru.homononsapiens.bankapi.DAO.account.AccountDAO;
import ru.homononsapiens.bankapi.DAO.card.Card;
import ru.homononsapiens.bankapi.utils.Util;

import java.util.List;

@Service
@AllArgsConstructor
public class OperationService {
    private OperationDAO operationDAO;

    public List<Operation> getAll() {
        return operationDAO.findAll();
    }

    public void add(Operation operation) {
        operation.setStatus("waiting");
        operationDAO.save(operation);
    }

    public void confirm(Operation operation) {
        operationDAO.confirm(operation.getId());
    }
}