package com.zergatstage.seminar03.task01;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@XmlRootElement(name = "student")
public class Student implements Serializable {
    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "age")
    private int age;
    //don't requires any annotations
    transient double GPA;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", GPA=" + GPA +
                '}';
    }
}
