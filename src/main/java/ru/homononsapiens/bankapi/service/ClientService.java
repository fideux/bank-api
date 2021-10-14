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

    public Long add(Client client) {
        return clientDao.save(client);
    }
}
