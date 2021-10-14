package ru.homononsapiens.bankapi.utils;

public class Util {

    public static String generateCardNumber() {
        return Long.toString((long) (Math.random() * (1_0000_0000_0000_0000L - 1000_0000_0000_0000L)));
    }

    public static String generateAccountNumber() {
        return Long.toString((long) (Math.random() * (1_00000_00000L - 10000_00000L)))
                + Long.toString((long) (Math.random() * (10_00000_00000L - 10000_00000L))).substring(1);
    }
}
