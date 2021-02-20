package com.sparta.aaron.EmployeesFiles;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class EmployeeManager {

    public static List<Employees> employeesList = new ArrayList<>();
    public static Hashtable<Integer, Employees> employeesTable = new Hashtable<>();
    public static List<Employees> duplicates = new ArrayList<>();


    public static void newEmployee(String[] employeeArray){
        Employees employee = new Employees(Integer.parseInt(employeeArray[0]), employeeArray[1], employeeArray[2],
                employeeArray[3], employeeArray[4], employeeArray[5].charAt(0), employeeArray[6], employeeArray[7],
                employeeArray[8], Integer.parseInt(employeeArray[9]));

        if(employeesTable.get(employee.empId) == null) {
            employeesTable.put(employee.empId, employee);
            employeesList.add(employee);
        } else {
            duplicates.add(employee);
        }
    }

    public static void newEmployee(int empId, String title, String firstName, String middleInitial, String lastName, char gender,
                                   String email, String dob, String joinDate, int salary) {

        Employees employee = new Employees(empId, title, firstName, middleInitial, lastName, gender, email, dob,
                joinDate, salary);

        if(employeesTable.get(employee.empId) == null) {
            employeesTable.put(employee.empId, employee);
            employeesList.add(employee);
        } else {
            duplicates.add(employee);
        }
    }
}
