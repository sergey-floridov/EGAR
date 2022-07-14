package ru.pnz.floridov.RestDemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Card")
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "cc_number")
    private Integer ccNumber;

    @Column(name = "payment_system")
    private String paymentSystem;

    @OneToOne(optional=false, mappedBy="card")
    @JoinColumn(name = "debet_account_id", unique = true, referencedColumnName = "id")
    private DebetAccount debetAccount;



}