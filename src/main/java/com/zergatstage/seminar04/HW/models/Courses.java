package com.zergatstage.seminar04.HW.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.Random;

@Entity

public class Courses {
    //fabric
    private static final Random random = new Random();
    private static final String[] coursesTitles = {"BS Architecture", "BS Chemical Engineering", "BS Computer Science"
            , "BS Information Technology", "BS Nursing", "BS Pharmacy", "BS Criminology", "BS Business Administration"
            , "BS Accountancy", "BS Tourism Management", "BS Hotel and Restaurant Management", "BS Psychology"
            , "BS Education", "BS Mathematics", "BS Biology", "BS Physics", "BS Chemistry", "BS Environmental Science"
            , "BS Communication Arts", "BS Journalism"};

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    private int id;
    @Getter
    private String title;
    @Getter
    private int duration;

    public Courses() {
    }

    public Courses(int id, String title, int duration) {
        this.id = id;
        this.title = title;
        this.duration = duration;
    }

    public Courses(String title, int duration) {
        this.title = title;
        this.duration = duration;
    }

    public static Courses create(){
        return new Courses(coursesTitles[random.nextInt(coursesTitles.length)], random.nextInt(5) );
    }

    @Override
    public String toString() {
        return "Courses{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", duration=" + duration +
                '}';
    }
}
