package org.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TestSpring {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");

    //Music music = context.getBean("musicBean", Music.class);

        MusicPleer musicPleer = context.getBean("musicPleer", MusicPleer.class);

        musicPleer.playMusic();
        System.out.println(musicPleer.getName());
        System.out.println(musicPleer.getVolume());

        context.close();
    }
}
