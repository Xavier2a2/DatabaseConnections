package com.sparta.aaron;

import com.sparta.aaron.EmployeesFiles.CSVReader;
import com.sparta.aaron.EmployeesFiles.EmployeeManager;
import com.sparta.aaron.EmployeesFiles.EmployeesDAO;

public class Starter {
    public static void start(){

        //Read the Employee File, use the EmployeeManager to create and keep track of all Employees
        CSVReader.readEmployees("src/main/resources/EmployeeRecordsLarge.csv");

        //Create a table of employees in the SQL database
        EmployeesDAO employeesDAO = new EmployeesDAO();
        employeesDAO.createTableOfEmployees();

        // Using the EmployeesDAO, from the EmployeeManagers Hashtable and list of non-duplicate Employees,
        //create a sql insert statement and insert all the employees using threads to create the statement quickly
        employeesDAO.executeSqlRequest(EmployeesDAO
                .sqlInsertBuilderWithThreads(EmployeeManager.employeesList,EmployeeManager.employeesTable));



    }
}
