package ru.homononsapiens.bankapi;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.homononsapiens.bankapi.controller.ClientController;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientController clientController;

    @Test
    public void contextLoads() throws Exception {
        assertThat(clientController).isNotNull();
    }

    /**
     * Выпуск новой карты по счету
     */
    @Test
    public void addCardTest() throws Exception {
        mockMvc.perform(post("/api/client/card/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"id\": %d}", 1)))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    /**
     * Просмотр списка карт
     */
    @Test
    public void getCardsTest() throws Exception {
        mockMvc.perform(post("/api/client/card/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[*]").isNotEmpty());
    }

    /**
     * Внесение вредств
     */
    @Test
    public void doRefillTest() throws Exception {
        mockMvc.perform(post("/api/client/account/refill")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"accountId\": %d,\"amount\": %s}", 1, "100.99")))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    /**
     * Проверка баланса
     */
    @Test
    public void getAccountBalance() throws Exception {
        mockMvc.perform(post("/api/client/account/balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"id\": %d}", 1)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").isNotEmpty());
    }

    /**
     * Добавление контрагента
     */
    @Test
    public void addPartnerTest() throws Exception {
        String json = String.format("{\"name\":\"%s\",\"inn\":\"%s\",\"bik\":\"%s\"," +
                        "\"account_number\":\"%s\",\"clientId\":%d}", "ООО Рога и копыта",
                "1234567834", "123456732", "12345678912345678990", 1);

        mockMvc.perform(post("/api/client/partner/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }

    /**
     * Просмотр контрагентов
     */
    @Test
    public void getPartnersTest() throws Exception {
        mockMvc.perform(post("/api/client/partner/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"id\": %d}", 1)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[*]").isNotEmpty());
    }

    /**
     * Перевод средств контрагенту
     */
    @Test
    public void doPaymentTest() throws Exception {
        mockMvc.perform(post("/api/client/partner/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"accountId\": 1,\"partnerId\": 1,\"purpose\": \"Штраф за...\",\"amount\": 1}")))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty());
    }
}