package com.zergatstage.seminar03.task01;

import java.util.ArrayList;
import java.util.List;

import static com.zergatstage.seminar03.task01.Controller.readObjectFromFile;
import static com.zergatstage.seminar03.task01.Controller.saveObjectToFile;

public class App {
    public static void main(String[] args) {
        Student student = new Student();
        student.setName("Josh");
        student.setAge(27);
        student.setGPA(32);
        List<Student> listStudents = new ArrayList<>();
        listStudents.add(student);
        //Save to file
        String fileName = "studentObj.bin";
        saveObjectToFile(fileName, listStudents);
        //deserialize object
        readObjectFromFile("studentObj.bin");

    }


}
