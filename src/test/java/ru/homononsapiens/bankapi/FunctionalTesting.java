package ru.homononsapiens.bankapi;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.homononsapiens.bankapi.controller.ClientController;
import ru.homononsapiens.bankapi.controller.EmployeeController;

import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FunctionalTesting {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientController clientController;

    @Autowired
    private EmployeeController employeeController;

    /**
     * Выпуск карты и проверка ее отсутствия в списке
     */
    @Test
    public void addCardAndCheckForMissingTest() throws Exception {
        // Добавляем новую карту к счету
        MvcResult mvcResult = addCardByAccountId(1);

        // Получаем id добавленной карты
        long cardId = getValueFromJson(mvcResult, "$.id");

        // Проверяем, отсутствие карты в списке
        checkMissingCard(1, cardId);
    }

    /**
     * Выпуск карты и проверка ее присутсвия в списке
     */
    @Test
    public void addCardAndCheckPresence() throws Exception {
        // Добавляем новую карту к счету
        MvcResult mvcResult = addCardByAccountId(1);

        // Получаем id добавленной карты
        long cardId = getValueFromJson(mvcResult, "$.id");

        // Подтверждаем выпуск карты
        confirmCard(cardId);

        // Проверяем, присутствие карты в списке
        checkPresenceCard(1, cardId);
    }


    private long getValueFromJson(MvcResult mvcResult, String jsonPath) throws UnsupportedEncodingException {
        return Long.parseLong(JsonPath.parse(mvcResult.getResponse().getContentAsString()).read(jsonPath).toString());
    }

    private MvcResult addCardByAccountId(long accountId) throws Exception {
        return mockMvc.perform(post("/api/client/card/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"id\": %d}", accountId)))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andReturn();
    }

    private MvcResult checkMissingCard(long clientId, long cardId) throws Exception {
        return mockMvc.perform(post("/api/client/card/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"id\": %d}", clientId)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath(String.format("$[?(@.id == %d)]", cardId)).isEmpty())
                .andReturn();
    }

    private MvcResult confirmCard(long cardId) throws Exception {
        return mockMvc.perform(post("/api/employee/client/card/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"id\": %d}", cardId)))
                .andDo(print()).andExpect(status().isOk())
                .andReturn();
    }

    private MvcResult checkPresenceCard(long clientId, long cardId) throws Exception {
        return mockMvc.perform(post("/api/client/card/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"id\": %d}", clientId)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath(String.format("$[?(@.id == %d)]", cardId)).isNotEmpty())
                .andReturn();
    }
}
