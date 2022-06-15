package org.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("application.xml");

        // Создаем бин vipDepositor c конструктором по умолчанию (выводим sout),
        // создаем бин vipAccount c сеттерами, внедряем бин vipDepositor
        VipAccount account1 = context.getBean("vipAccount", VipAccount.class);

        // Создаем бин ordinaryDepositor c конструктором по умолчанию (выводим sout),
        // создаем бин ordinaryAccount c сеттерами, внедряем бин ordinaryDepositor
        OrdinaryAccount account2 = context.getBean("ordinaryAccount",OrdinaryAccount.class);

        account1.environment();
        account2.environment();
        context.close();



    }
}
