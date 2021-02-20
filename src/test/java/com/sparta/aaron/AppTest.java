package com.sparta.aaron;

import static org.junit.Assert.assertTrue;

import com.sparta.aaron.EmployeesFiles.CSVReader;
import com.sparta.aaron.EmployeesFiles.EmployeeManager;
import com.sparta.aaron.EmployeesFiles.EmployeesDAO;
import com.sparta.aaron.EmployeesFiles.EmployeesDTO;
import org.junit.Test;

public class AppTest {
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

    @Test
    public void test(){
        // Gets all the employees and puts them into the Employee list in Employees.class
        long start = System.currentTimeMillis();
        CSVReader.readEmployees("src/main/resources/EmployeeRecordsLarge.csv");
        long end = System.currentTimeMillis();
        Printer.printMessage("Time to read file:: "+ (end-start));

        // Takes all the employees and put them into a string that's executable in SQL
        start = System.currentTimeMillis();
        String sqlInsert = EmployeesDAO.sqlInsertBuilderWithThreads(EmployeeManager.employeesList,EmployeeManager.employeesTable);
        end = System.currentTimeMillis();
        Printer.printMessage("Time to make insert:: "+ (end-start));

        EmployeesDAO employeesDAO = new EmployeesDAO();
        employeesDAO.createTableOfEmployees();
        // then sends it to the database
        start = System.currentTimeMillis();

        employeesDAO.executeSqlRequest(sqlInsert);
        end = System.currentTimeMillis();
        Printer.printMessage("Time to make database:: "+ (end-start));

    }

}
