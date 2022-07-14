package ru.pnz.floridov.RestDemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;


    @NotEmpty(message = "Имя не может быть пустым")
    @Size(min = 2, max = 100, message = "Имя должно быть от 2 до 100 символов длиной")
    @Column(name = "first_name")
    private String firstName;

    @NotEmpty(message = "Фамилия не должна быть пустой")
    @Size(min = 2, max = 100, message = "Фамилия должна быть от 2 до 100 символов длиной")
    @Column(name = "last_name")
    private String lastName;


    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private int phone;

    @Column(name = "email")
    @Email
    @NotEmpty(message = "Емэйл не должен быть пустым")
    private String email;


    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private Manager manager;


    @OneToOne
    @JoinColumn(name = "debet_account_id", referencedColumnName = "id")
    private DebetAccount debetAccount;

    @OneToMany(mappedBy = "client", fetch=FetchType.LAZY)
    @ToString.Exclude
    private List<CreditProduct> credits;

}