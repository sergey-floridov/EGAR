package ru.pnz.floridov.RestDemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pnz.floridov.RestDemo.util.Currency;
import ru.pnz.floridov.RestDemo.util.CreditType;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Credit_product")
public class CreditProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @Column(name = "current_account", unique = true)
    private Long currentAccount;

    @Column(name = "type")
    private CreditType creditType;



    @Min(2)
    @Max(300)
    @Column(name = "loan_period_in_month")
    private Integer loanPeriodInMonth;

    @Column(name = "amount")
    private BigDecimal amount;

    private BigDecimal loanBalance;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "insurance")
    private String insurance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;


    @Transient
    private LocalDate takenAt;

    private LocalDate lastPaidAt;





    @Transient
    private boolean expired; // Hibernate не будет замечать этого поля, что нам и нужно. По-умолчанию false


}
