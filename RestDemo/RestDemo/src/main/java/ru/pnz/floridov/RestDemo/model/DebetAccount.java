package ru.pnz.floridov.RestDemo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ru.pnz.floridov.RestDemo.util.Currency;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Debet_account")
public class DebetAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "current_account")
    private Long currentAccount;


    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    private Currency currency;

    @Column(name = "rate")
    private BigDecimal rate;



    @OneToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    @JsonIgnore
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    @OneToOne(mappedBy = "debetAccount", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private Card card;


}

