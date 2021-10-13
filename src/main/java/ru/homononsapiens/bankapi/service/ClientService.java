package ru.homononsapiens.bankapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.homononsapiens.bankapi.model.Client;
import ru.homononsapiens.bankapi.dao.ClientDao;
import ru.homononsapiens.bankapi.utils.Util;
import java.util.List;

@Service
@AllArgsConstructor
public class ClientService {

    private ClientDao clientDao;

    public List<Client> getAll() {
        return clientDao.getAll();
    }

    public JsonNode add(Client client) {
        if (clientDao.save(client)) {
            return Util.getMessageAsJsonObject("OK", "Клиент успешно создан");
        }
        return Util.getMessageAsJsonObject("Error", "Ошибка при создании клиента");
    }
}