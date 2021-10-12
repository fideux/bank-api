package ru.homononsapiens.bankapi.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Util {

    public static String generateCardNumber() {
        return Long.toString((long) (Math.random() * (1_0000_0000_0000_0000L - 1000_0000_0000_0000L)));
    }

    public static String generateAccountNumber() {
        return Long.toString((long) (Math.random() * (1_00000_00000L - 10000_00000L)))
                + Long.toString((long) (Math.random() * (10_00000_00000L - 10000_00000L))).substring(1);
    }

    public static JsonNode getMessageAsJsonObject(String title, String info) {
        JsonNode node = null;
        try {
            ObjectMapper mapper = new ObjectMapper();
            node = mapper.readValue("{\"" + title + "\": \"" + info+ "\"}", JsonNode.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return node;
    }
}
