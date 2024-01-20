package com.zergatstage.seminar04.task01;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App {
    /**
     * Задача 1
     * ========
     * Используя SQL, создайте таблицу students с полями id (ключ), name, и age.
     * Реализация подключения к базе данных через JDBC:
     * Напишите Java-код для подключения к базе данных (например, MySQL или PostgreSQL).
     * Реализуйте вставку, чтение, обновление и удаление данных в таблице Students
     * с использованием провайдера JDBC.
     */
    private static Random random = new Random();
    public static void main(String[] args) {
        String url = "jdbc:mariadb://localhost:3306";
        String user = "root";
        String pass = "mypass";
        String dataBaseName = "studentsDB";

        try {
            Connection connection = DriverManager.getConnection(url, user, pass);
            createDataBase(connection, dataBaseName);
            DatabaseMetaData metaData = connection.getMetaData();
            System.out.println(metaData.getDatabaseProductName() + " # "
                    + metaData.getDatabaseProductVersion());

            useDataBase(connection, dataBaseName);
            // create set of columns
            //TODO: if created as entity - use reflection to build a query instead a raw fields creation
            List<TableConstructorFields> fieldsList = new ArrayList<>();
            fieldsList.add(new TableConstructorFields("id", "INT", 0, "AUTO_INCREMENT PRIMARY KEY"));
            fieldsList.add(new TableConstructorFields("name", "VARCHAR", 255, ""));
            fieldsList.add(new TableConstructorFields("age", "INT", 0, ""));

            createTable(connection,fieldsList,"students");
            //insert data to the database
            for (int i = 0; i < random.nextInt(12); i++) {
                insertData(connection,Student.create());
            }
            //read data
            List<Student> studentList = readStudents(connection);
            System.out.println("Fetched from DB: students = " + studentList.size());


            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    /**
     * Fetch all data from students table
     * @param connection Current database connection
     * @return List of Student references
     * @throws SQLException Throw new exception
     */
    private static List<Student> readStudents(Connection connection) throws SQLException{
        ArrayList<Student> studentArrayList = new ArrayList<>();
        String readSQL = "SELECT * FROM students;";
        try(PreparedStatement statement = connection.prepareStatement(readSQL)){
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                studentArrayList.add(new Student(id, name, age));
            }
            return studentArrayList;
        }
    }

    /**
     * Creates a new database studentsDB
     *
     * @param connection   Existed connection to the database
     * @param dataBaseName New schema name
     */
    private static void createDataBase(Connection connection, String dataBaseName) throws SQLException {
        String createDataBaseSQL = String.format("CREATE DATABASE IF NOT EXISTS %s;", dataBaseName);
        PreparedStatement preparedStatement = connection.prepareStatement(createDataBaseSQL);
        preparedStatement.execute();
    }

    /**
     * Select created dataBase as active. Uses try-with-resources
     *
     * @param connection   current connection
     * @param dataBaseName schema name
     * @throws SQLException must be implemented
     */
    private static void useDataBase(Connection connection, String dataBaseName) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(String.format("USE %s", dataBaseName))) {
            statement.execute();
        }
    }

    /**
     * Creates a table with defined fields and options
     * @param connection Current connection
     * @param fields List of fields TableConstructorFields
     * @param tableName Simple table name
     * @throws SQLException must be intercepted in the uppers  methods
     */
    private static void createTable(Connection connection, List<TableConstructorFields> fields, String tableName) throws SQLException {
        StringBuilder command = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        command.append(tableName).append("(");
        fields.stream()
                .forEach(tableConstructorFields -> {
                    command.append(tableConstructorFields.getColumnName())
                            .append(" " + tableConstructorFields.getColumnDataType() + " ")
                            .append(
                                    (tableConstructorFields.getColumnSize() > 0) ? "(" + tableConstructorFields.getColumnSize() + ")"
                                            : "")
                            .append((!tableConstructorFields.getOptions().isEmpty()) ? tableConstructorFields.getOptions() : "")
                            .append(",");
                });
        command.delete(command.length()-1, command.length()); //last comma delete
        command.append(");");
        String sqlCreateTable = command.toString();
        try (PreparedStatement statement = connection.prepareStatement(sqlCreateTable);){
            statement.execute();
        }
    }


    /**
     * Inserts single students data into table students
     * @param connection Current connection
     * @param student Student object
     * @throws SQLException Throws SQL exception
     */
    public static int insertData(Connection connection, Student student) throws SQLException {
        String insertSQLPattern = "INSERT INTO students (name, age) VALUES (?,?);";
        try( PreparedStatement statement = connection.prepareStatement(insertSQLPattern)) {
            statement.setString(1, student.getName());
            statement.setString(2, Integer.toString(student.getAge()));
            return statement.executeUpdate();
        }
    }

}
