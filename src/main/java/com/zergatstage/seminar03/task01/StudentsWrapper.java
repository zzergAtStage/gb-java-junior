package com.zergatstage.seminar03.task01;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "students")
public class StudentsWrapper {
    private List<Student> students;
    public StudentsWrapper(){};
    @XmlElement(name = "student")
    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Student> getStudents() {
        return students;
    }
}
