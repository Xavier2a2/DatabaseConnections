package com.sparta.aaron.EmployeesFiles;

import com.sparta.aaron.Printer;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.*;


public class EmployeesDAO implements AutoCloseable {
    private String URL = "jdbc:mysql://localhost:3306/myLocal?serverTimeZone=GMT";
    private Connection connection = null;
    private Properties properties = new Properties();

    private final String createTable = "CREATE TABLE Employees (EmpID INT PRIMARY KEY, Title VARCHAR(5), " +
            "FirstName VARCHAR(15), MiddleInitial CHAR(1), LastName VARCHAR(15), Gender CHAR(1), Email VARCHAR(50), " +
            "DateOfBirth DATE, JoinDate DATE, Salary INT );";

    public static String insertAllEmployeesBuilder = "INSERT INTO Employees(EmpID, Title, FirstName, MiddleInitial," +
            " LastName, Gender, Email, DateOfBirth, JoinDate, Salary) VALUES ";

    public static String endingInsert = "";


    public Connection connectToDatabase() {
        try {
            properties.load(new FileReader("src/main/resources/login.properties"));
            connection = DriverManager.getConnection(URL, properties.getProperty("username"), properties.getProperty("password"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return connection;
    }

    public void createTableOfEmployees() {
        try {
            Statement statement = connectToDatabase().createStatement();
            statement.execute(createTable);
            close();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String sqlInsertBuilderForThreads(int startNum, int endNum, List<Employees> employeesList, Hashtable<Integer, Employees> employeesHashtable) {
        String s = "";
        for (int i = startNum; i < endNum; i++) {
            Employees employee = employeesHashtable.get(employeesList.get(i).empId);
            s += "(" + employee.empId + ",'"
                    + employee.title + "','"
                    + employee.firstName + "','"
                    + employee.middleInitial + "','"
                    + employee.lastName + "','"
                    + employee.gender + "','"
                    + employee.email + "','"
                    + Employees.parseDate(employeesList.get(i).dob) + "','"
                    + Employees.parseDate(employeesList.get(i).joinDate) + "',"
                    + employee.salary +
                    ")";

            if (i != employeesList.size() - 1) {
                s += ",";
            }
        }
        if (endNum != employeesHashtable.size()) {
            insertAllEmployeesBuilder += s;
        } else {
            endingInsert += s;
        }
        return s;
    }

    public static String sqlInsertBuilderWithThreads(List<Employees> employeesList, Hashtable<Integer, Employees> employeesHashtable) {

        int start;
        int end = 100;
        List<Thread> threadList = new ArrayList<>();

        for (start = 0; start < employeesHashtable.size();
             end += (employeesHashtable.size() > end + 500) ? 500 : employeesHashtable.size() - end) {
            int finalStart = start;
            int finalEnd = end;
            Thread thread = new Thread() {
                String s = EmployeesDAO.sqlInsertBuilderForThreads(finalStart, finalEnd, employeesList, employeesHashtable);
            };
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            threadList.add(thread);
            start = end;
        }

        insertAllEmployeesBuilder += endingInsert + ";";
        return insertAllEmployeesBuilder;
    }

    public static String sqlInsertBuilder(Hashtable<Integer, Employees> employeesTable, List<Employees> employeesList) {
        String finalString = "INSERT INTO Employees(EmpID, Title, FirstName, Middle, LastName, Gender, Email, DateOfBirth, JoinDate, Salary) " +
                "VALUES ";


        String s = "";
        for (int i = 0; i < employeesList.size(); i++) {
            Employees employee = employeesTable.get(employeesList.get(i).empId);
            s += "(" + employee.empId + ",'"
                    + employee.title + "','"
                    + employee.firstName + "','"
                    + employee.middleInitial + "','"
                    + employee.lastName + "','"
                    + employee.gender + "','"
                    + employee.email + "','"
                    + Employees.parseDate(employeesList.get(i).dob) + "','"
                    + Employees.parseDate(employeesList.get(i).joinDate) + "',"
                    + employee.salary +
                    ")";

            if (i != employeesList.size() - 1) {
                s += ",";
            }
        }
        finalString += s + ";";
        return finalString;
    }

    public void executeSqlRequest(String sqlRequest) {
        try {
            PreparedStatement preparedStatement = connectToDatabase().prepareStatement(sqlRequest);
            preparedStatement.execute();
            close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Printer.printMessage("Process has finished");
        }
    }

    @Override
    public void close() throws Exception {

    }
}

