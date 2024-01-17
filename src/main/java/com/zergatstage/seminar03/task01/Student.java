package com.zergatstage.seminar03.task01;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@XmlRootElement(name = "student")
@JsonIgnoreProperties(value = { "y" })
public class Student implements Serializable {
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @XmlElement(name = "name")
    private String name;
    @XmlElement(name = "age")
    private int age;

    @JsonIgnore
    public double getGPA() {
        return GPA;
    }

    //don't requires any annotations
    @JsonIgnore
    private transient double GPA;

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", GPA=" + GPA +
                '}';
    }
}
