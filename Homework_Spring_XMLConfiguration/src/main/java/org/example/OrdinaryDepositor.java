package org.example;

public class OrdinaryDepositor implements Depositor{
    @Override
    public void greetings() {
        System.out.println("Здравствуйте! Что Вы хотели?");
    }

    public OrdinaryDepositor() {

        System.out.println("Приветствие обычного вкладчика");
        System.out.println("Здравствуйте! Что Вы хотели?");
        System.out.println("======================");
    }
}
