package ru.homononsapiens.bankapi.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class BalanceResponse {
    private BigDecimal balance;
}
