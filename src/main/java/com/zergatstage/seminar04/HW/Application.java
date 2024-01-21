package com.zergatstage.seminar04.HW;

import com.zergatstage.seminar04.HW.models.Courses;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Application {
    private static List<Courses> coursesList;
    public static void main(String[] args) {

        SessionFactory sessionFactory = HibernateConfiguration.getConfiguration()
                .buildSessionFactory();
        try(Session session = sessionFactory.getCurrentSession()){
            session.beginTransaction();
            Courses courses = Courses.create();
            session.save(courses);

            Courses resultObject = session.get(Courses.class, courses.getId());
            System.out.println(resultObject);
            session.getTransaction().commit();
        }

        try(Session session = sessionFactory.getCurrentSession()){
            //add a few courses
            Transaction tx = session.beginTransaction();
            coursesList = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                coursesList.add(Courses.create());
                session.saveOrUpdate(coursesList.get(i));
            }
            session.flush();
            tx.commit();
        }

        try(Session session =sessionFactory.getCurrentSession()){
            Transaction tx = session.beginTransaction();
            //            //delete random object
            Courses toDeleteObject = coursesList.get(new Random().nextInt(12));

            session.delete(toDeleteObject);
            System.out.println(toDeleteObject);
            tx.commit();
        }

    }
}
