package com.zergatstage.seminar04.task02;

import com.zergatstage.seminar04.task01.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {


        SessionFactory sessionFactory = new Configuration()
                .configure(new File("./src/main/resources/hibernate.cfg.xml"))
                .addAnnotatedClass(Student.class)
                .buildSessionFactory();

        try (Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();

            Student student = Student.create();
            session.save(student);

            Student retrievedObject = session.get(Student.class, student.getId());
            System.out.println("retrievedObject: " + retrievedObject.toString());
            session.getTransaction().commit();
            System.out.println("Committed ...");
        }

    }
}
