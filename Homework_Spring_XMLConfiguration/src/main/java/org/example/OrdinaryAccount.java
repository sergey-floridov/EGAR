package org.example;

public class OrdinaryAccount implements Account {
    int id;
    OrdinaryDepositor ordinaryDepositor;
    String password;

    public void setId(int id) {
        this.id = id;
    }

    public void setOrdinaryDepositor(OrdinaryDepositor ordinaryDepositor) {
        this.ordinaryDepositor = ordinaryDepositor;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public OrdinaryAccount() {
    }

    public OrdinaryAccount(int id, OrdinaryDepositor ordinaryDepositor, String password) {
        this.id = id;
        this.ordinaryDepositor = ordinaryDepositor;
        this.password = password;
    }

    @Override
    public String toString() {
        return "OrdinaryAccount{" +
                "id=" + id +
                ", ordinaryDepositor=" + ordinaryDepositor +
                ", password='" + password + '\'' +
                '}';
    }

    @Override
    public void environment() {
        System.out.println("Подождите пять минут, сейчас Вами займутся!");
    }
}

