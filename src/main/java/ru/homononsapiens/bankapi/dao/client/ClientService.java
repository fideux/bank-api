package ru.homononsapiens.bankapi.dao.client;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.homononsapiens.bankapi.utils.Util;

import java.util.List;

@Service
@AllArgsConstructor
public class ClientService {

    private ClientDao clientDao;

    public List<Client> findAll() {
        return clientDao.findAll();
    }

    public JsonNode save(Client client) {
        if (clientDao.save(client)) {
            return Util.getMessageAsJsonObject("OK", "Клиент успешно создан");
        }
        return Util.getMessageAsJsonObject("Error", "Ошибка при создании клиента");
    }
}
