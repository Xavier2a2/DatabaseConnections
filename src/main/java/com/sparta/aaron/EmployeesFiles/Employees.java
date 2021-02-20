package com.sparta.aaron.EmployeesFiles;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Employees {
    int empId;
    String title;
    String firstName;
    String middleInitial;
    String lastName;
    char gender;
    String email;
    String dob;
    String joinDate;
    int salary;




    //Takes in the different columns of data for each employee then adds it to a hashtable
    //If the employee's id number is already in the hashmap it stores the duplicate in a separate array for duplicates
    public Employees(int empId, String title, String firstName, String middleInitial, String lastName, char gender,
                     String email, String dob, String joinDate, int salary) {
        this.empId = empId;
        this.title = title;
        this.firstName = firstName;
        this.middleInitial = middleInitial;
        this.email = email;
        this.lastName = lastName;
        this.gender = gender;
        this.dob = dob;
        this.joinDate = joinDate;
        this.salary = salary;
    }


    public String getEmail() {
        return email;
    }

    public int getEmpId() { return empId; }

    public String getTitle() { return title; }

    public String getFirstName() { return firstName; }

    public String getMiddleInitial() { return middleInitial; }

    public String getLastName() { return lastName; }

    public char getGender() { return gender; }

    public String getDob() { return dob; }

    public String getJoinDate() { return joinDate; }

    public int getSalary() { return salary; }

    public static java.sql.Date parseDate (String date){
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH);
        java.sql.Date sqlDate = null;
        try{
            java.util.Date tempDate =  formatter.parse(date);
            sqlDate = new java.sql.Date(tempDate.getTime());

        } catch(ParseException e){
            e.printStackTrace();
        }
        return sqlDate;
    }

    public static String formatDate(java.sql.Date sqlDate){
        SimpleDateFormat formatter = new SimpleDateFormat("E dd MMM yyyy", Locale.ENGLISH);
        return formatter.format(sqlDate);
    }

}
