package org.example;

public class VipAccount implements Account{

    int id;
    VIPDepositor vipDepositor;
    String password;

    @Override
    public String toString() {
        return "VipAccount{" +
                "id=" + id +
                ", vipDepositor=" + vipDepositor +
                ", password='" + password + '\'' +
                '}';
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setVipDepositor(VIPDepositor vipDepositor) {
        this.vipDepositor = vipDepositor;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public VipAccount() {
    }

    public VipAccount(int id, VIPDepositor vipDepositor, String password) {
        this.id = id;
        this.vipDepositor = vipDepositor;
        this.password = password;
    }

    @Override
    public void environment() {
        System.out.println("Для Вас открыт специальный расчетный счет!");
    }
}
