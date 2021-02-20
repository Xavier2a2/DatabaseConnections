package com.sparta.aaron;

import com.sparta.aaron.EmployeesFiles.EmployeesDTO;
import org.junit.Test;


public class EmployeeDTOTest {

    @Test
    public void viewEmployeeTest(){
        EmployeesDTO employeesDTO = new EmployeesDTO();
        employeesDTO.viewEmployee("EmpID", "2002");
    }

    @Test
    public void viewFromEmployeeTest(){
        EmployeesDTO employeesDTO = new EmployeesDTO();
        employeesDTO.viewFromEmployee("FirstName","EmpID", "2002");
    }


}
