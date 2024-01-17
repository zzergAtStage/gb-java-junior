package com.zergatstage.seminar03.task01;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final XmlMapper xmlMapper = new XmlMapper();

    public static void saveObjectToFile(String fileName, List<Student> students) {
        try {
            if (fileName.endsWith(".json")) {
                objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                objectMapper.writeValue(new File(fileName), students);
            } else if (fileName.endsWith(".bin")) {
                try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
                    oos.writeObject(students);
                }
            } else if (fileName.endsWith(".xml")) {

                xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                //preparing students to serialize
                StudentsWrapper studentsWrapper = new StudentsWrapper();
                studentsWrapper.setStudents(students);
                xmlMapper.writeValue(new File(fileName), studentsWrapper);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readObjectFromFile(String fileName) {
        File file = new File(fileName);
        ArrayList students = new ArrayList<>();
        if (file.exists()) {
            if (fileName.endsWith(".bin")) {
                try (FileInputStream fileIn = new FileInputStream(fileName);
                     ObjectInputStream in = new ObjectInputStream(fileIn)) {
                    //hardcode for testing TODO: replace
                    List<Student> student = (List) in.readObject();
                    showStudents(student);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else if (fileName.endsWith(".xml")) {
                try {
                    xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
                    StudentsWrapper studentsXML = xmlMapper.readValue(new File(fileName), StudentsWrapper.class);
                    showStudents(studentsXML.getStudents());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void showStudents(List<Student> student) {
        System.out.println("Restored objects:");
        student.stream().forEach(System.out::println);
    }
}
