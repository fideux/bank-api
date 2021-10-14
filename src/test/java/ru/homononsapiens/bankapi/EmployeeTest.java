package ru.homononsapiens.bankapi;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.homononsapiens.bankapi.controller.EmployeeController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EmployeeTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmployeeController employeeController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(employeeController).isNotNull();
    }

    /**
     * Добавление нового физ-лица
     */
    @Test
    public void addClientTest() throws Exception {
        mockMvc.perform(post("/api/employee/client/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Джордж Оруэлл\",\"dob\":\"1903-06-25\"}"))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    /**
     * Открытие счета
     */
    @Test
    public void addAccountTest() throws Exception {
        mockMvc.perform(post("/api/employee/client/account/open")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"balance\":\"100.00\",\"clientId\":1}"))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    /**
     * Подтверждение выпуска карты
     */
    @Test
    public void confirmCardTest() throws Exception {
        mockMvc.perform(post("/api/employee/client/card/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"id\": %d}", 1)))
                .andDo(print()).andExpect(status().isOk());
    }

    /**
     * Подтверждение операции пополнения
     */
    @Test
    public void confirmRefillTest() throws Exception {
        mockMvc.perform(post("/api/employee/client/refill/confirm")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"id\": %d}", 1)))
                .andDo(print()).andExpect(status().isOk());
    }

    /**
     * Список клиентов
     */
    @Test
    public void getClientsTest() throws Exception {
        mockMvc.perform(get("/api/employee/client/list"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[*]").isNotEmpty());
    }

    /**
     * Список счетов
     */
    @Test
    public void getAccountsTest() throws Exception {
        mockMvc.perform(get("/api/employee/client/account/list"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[*]").isNotEmpty());
    }

    /**
     * Список выпущенных карт
     */
    @Test
    public void getAllCardsTest() throws Exception {
        mockMvc.perform(get("/api/employee/client/card/list"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[*]").isNotEmpty());
    }

    /**
     * Список операций пополнений
     */
    @Test
    public void getRefillsTest() throws Exception {
        mockMvc.perform(get("/api/employee/client/refill/list"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[*]").isNotEmpty());
    }

    /**
     * Список операций перевода средств контрагенту
     */
    @Test
    public void getPaymentsTest() throws Exception {
        mockMvc.perform(get("/api/employee/client/payment/list"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[*]").isNotEmpty());
    }
}
