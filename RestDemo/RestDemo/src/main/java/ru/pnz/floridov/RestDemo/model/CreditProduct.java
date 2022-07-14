package ru.pnz.floridov.RestDemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.Manager;
import ru.pnz.floridov.RestDemo.util.Currency;
import ru.pnz.floridov.RestDemo.util.Type;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
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

//
//    @Min(value = 10, message = "Введите 10 цифр")
//    @Max(value = 10, message = "Введите 10 цифр")
    @Column(name = "current_account")
    private Integer currentAccount;

    @Column(name = "type")
    private Type type;


//    @NotEmpty(message = "Поставьте срок кредита")
    @Min(2)
    @Max(300)
    @Column(name = "loan_period_in_month")
    private Integer loanPeriodInMonth;


    @Column(name = "amount")
    private BigDecimal amount;

    @Column(name = "currency")
    private Currency currency;

    @Column(name = "rate")
    private BigDecimal rate;

    @Column(name = "insurance")
    private String insurance;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;

    private Date takenAt;





    @Transient
    private boolean expired; // Hibernate не будет замечать этого поля, что нам и нужно. По-умолчанию false

}
