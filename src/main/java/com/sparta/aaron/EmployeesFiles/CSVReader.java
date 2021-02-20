package com.sparta.aaron.EmployeesFiles;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {

    public static void readEmployees(String employeeFileName){
        try {
            BufferedReader br = new BufferedReader(new FileReader(employeeFileName));

            String row;
            br.readLine();
            while ((row = br.readLine()) != null) {
                String[] columns = row.split(",");
                EmployeeManager.newEmployee(columns);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}
