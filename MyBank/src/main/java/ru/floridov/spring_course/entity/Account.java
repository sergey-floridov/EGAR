package ru.floridov.spring_course.entity;

import javax.persistence.*;

@Entity
@Table(name="account")
public class Account {


    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;



    @Column(name = "amount")
    private int amount;




    @ManyToOne
    @JoinColumn(name = "client_id",referencedColumnName = "id")
    private Client owner;


    public Account(int amount, Client owner) {
        this.amount = amount;
        this.owner = owner;
    }

    public Account() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", amount=" + amount +
                ", owner=" + owner +
                '}';
    }
}
