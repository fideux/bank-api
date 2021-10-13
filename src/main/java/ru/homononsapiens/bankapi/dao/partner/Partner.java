package ru.homononsapiens.bankapi.dao.partner;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Getter
@Setter
@ToString
@Table(name = "partners")
public class Partner {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String inn;

    private String bik;

    private String account_number;

    @Column(name = "client_id")
    private Long clientId;
}
