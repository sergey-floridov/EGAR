package org.example;

import org.example.model.Director;
import org.example.model.Movie;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        Configuration configuration = new Configuration().addAnnotatedClass(Director.class).
                addAnnotatedClass(Movie.class);

        SessionFactory sessionFactory = configuration.buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();

        try {
            session.beginTransaction();

            // получаем режиссера и список его фильмов
            Director director1 = session.get(Director.class,2);
            System.out.println(director1);
            List<Movie> movies1 = director1.getMovies();
            System.out.println(movies1);

            //получаем фильм и его режиссера
            Movie movie1 = session.get(Movie.class,8);
            Director owner1 = movie1.getOwner();
            System.out.println(movie1);
            System.out.println(owner1);

            // добавляем новый фильм первому режиссеру
            Movie newMovie = new Movie("Back to the future", 1999, director1);
            session.save(newMovie);
            director1.getMovies().add(newMovie);  // так ЖЕЛАТЕЛЬНО делать, чтобы избежать проблем при кэшировании Хибернейтом
            System.out.println(newMovie);

            // создаем нового режиссера и новый фильм и связываем их
            Director director2 = new Director("Cameron", 60);
            Movie movie2 = new Movie("Titanic", 1997, director2);
            director2.setMovies(new ArrayList<>(Collections.singletonList(movie2)));
            session.save(director2);
            session.save(movie2);






            session.getTransaction().commit();
        }finally {
            sessionFactory.close();
        }


    }
}
