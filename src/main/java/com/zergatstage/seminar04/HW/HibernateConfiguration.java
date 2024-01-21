package com.zergatstage.seminar04.HW;

import com.zergatstage.seminar04.HW.models.Courses;
import org.hibernate.cfg.Configuration;

public class HibernateConfiguration {

    public static Configuration getConfiguration() {
        Configuration configuration = new Configuration();

        // Set JDBC driver
        configuration.setProperty("hibernate.connection.driver_class", "org.mariadb.jdbc.Driver");

        // Set database URL
        configuration.setProperty("hibernate.connection.url", "jdbc:mariadb://localhost:3306/CoursesDB?createDatabaseIfNotExist=TRUE");

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MariaDBDialect");
        configuration.setProperty("hibernate.connection.username", "root");
        configuration.setProperty("hibernate.connection.password", "mypass");
        configuration.setProperty("hibernate.show_sql", "true");
        configuration.setProperty("hibernate.hbm2ddl.auto", "update");
        //session configure
        configuration.setProperty("hibernate.current_session_context_class", "thread");


        configuration.addAnnotatedClass(Courses.class);


        return configuration;
    }
}
