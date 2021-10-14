package ru.homononsapiens.bankapi;


import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.homononsapiens.bankapi.controller.ClientController;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    @Test
    public void addCardTest() throws Exception {
        addCardByAccountId(1);
    }

    @Test
    public void addPartnerTest() throws Exception {
        String json = String.format("{\"name\":\"%s\",\"inn\":\"%s\",\"bik\":\"%s\"," +
                "\"account_number\":\"%s\",\"clientId\":%d}", "ООО Рога и копыта",
                "1234567834", "123456732", "12345678912345678990", 1);

        mockMvc.perform(post("/api/client/partner/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andReturn();
    }

    @Test
    public void getCardsTest() throws Exception {
        mockMvc.perform(post("/api/client/card/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":1}"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").isNotEmpty())
                .andReturn();
    }

    @Test
    public void getPartnersTest() throws Exception {
        mockMvc.perform(post("/api/client/partner/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"id\": %d}", 1)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$[*]").isNotEmpty())
                .andReturn();
    }

    @Test
    public void doRefillTest() throws Exception {
        mockMvc.perform(post("/api/client/account/refill")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"accountId\": %d,\"amount\": %s}", 1, "100.99")))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andReturn();
    }

    @Test
    public void doPaymentTest() throws Exception {
        mockMvc.perform(post("/api/client/partner/payment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"accountId\": 1,\"partnerId\": 1,\"purpose\": \"Штраф за...\",\"amount\": 90}")))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andReturn();
    }

    @Test
    public void getAccountBalance() throws Exception {
        mockMvc.perform(post("/api/client/account/balance")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"id\": %d}", 1)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").isNotEmpty())
                .andReturn();
    }


    @Test
    public void addCardAndCheckForMissingTest() throws Exception {
        // Добавляем новую карту к счету
        MvcResult mvcResult = addCardByAccountId(1);

        // Получаем id добавленной карты
        long cardId = jsonPath(mvcResult, "$.id");

        // Проверяем, отсутствие карты в списке
        checkMissingCard(1, cardId);
    }

    @Test
    public void addCardAndCheckPresence() throws Exception {
        // Добавляем новую карту к счету
        MvcResult mvcResult = addCardByAccountId(1);

        // Получаем id добавленной карты
        long cardId = jsonPath(mvcResult, "$.id");

        // Подтверждаем выпуск карты
        confirmCard(cardId);

        // Проверяем, присутствие карты в списке
        checkPresenceCard(1, cardId);
    }






    private long jsonPath(MvcResult mvcResult, String jsonPath) throws UnsupportedEncodingException {
        return Long.parseLong(JsonPath.parse(mvcResult.getResponse().getContentAsString()).read(jsonPath).toString());
    }

    private MvcResult addCardByAccountId(long accountId) throws Exception {
        return mockMvc.perform(post("/api/client/card/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"id\": %d}", accountId)))
                .andDo(print()).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andReturn();
    }

    private MvcResult checkMissingCard(long clientId, long cardId) throws Exception {
        return mockMvc.perform(post("/api/client/card/list")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(String.format("{\"id\": %d}", clientId)))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath(String.format("$[?(@.id == %d)]", cardId)).isEmpty())
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
                .andExpect(MockMvcResultMatchers.jsonPath(String.format("$[?(@.id == %d)]", cardId)).isNotEmpty())
                .andReturn();
    }

//    @Test
//    public void test() throws Exception {
//
//    this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().is4xxClientError())
//                .andExpect(content().string(containsString("Hello, World")));}

}