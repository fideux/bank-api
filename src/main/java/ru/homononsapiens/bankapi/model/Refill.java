package ru.homononsapiens.bankapi.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Table(name = "refills")
public class Refill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount", precision = 19, scale = 4)
    private BigDecimal amount;

    @Column(name = "account_id")
    private Long accountId;

    private String status = "waiting";

    public void setStatus(String status) {
        this.status = "waiting";
    }
}
