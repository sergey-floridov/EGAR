package org.example;

public class VIPDepositor implements Depositor{
    @Override
    public  void greetings() {
        System.out.println("Рады приветствовать Вас в нашем банке, для Вас специальные условия!");
    }

    public VIPDepositor() {

        System.out.println("Приветствие VIP клиента:");
        System.out.println("Рады приветствовать Вас в нашем банке, для Вас специальные условия!");
        System.out.println("====================================");

    };
    }

