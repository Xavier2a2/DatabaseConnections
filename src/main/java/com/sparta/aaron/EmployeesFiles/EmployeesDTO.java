package com.sparta.aaron.EmployeesFiles;

import com.sparta.aaron.Printer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

public class EmployeesDTO implements AutoCloseable{


    private final String selectEmployeesUsingParameters = "SELECT ? FROM Employees WHERE ? = ?";
    private final String updateEmployeeUsingParameters = "UPDATE Employees SET ?=? WHERE ?=?";
    private final String removeEmployee = "DELETE FROM Employees WHERE ?=?";
    private String[] employeeColumns = {"EmpID","Title","FirstName", "MiddleInitial","LastName","Gender","Email",
            "DateOfBirth","JoinDate","Salary"};
    private final String insertEmployee = "INSERT INTO Employees (EmpID, Title, FirstName, Middle, LastName, Gender," +
            " Email, DateOfBirth, JoinDate, Salary) VALUES (?,?,?,?,?,?,?,?,?,?)";

    private EmployeesDAO employeesDAO = new EmployeesDAO();

    public void viewFromEmployee(String selection, String column, String variable){
        if(Arrays.asList(employeeColumns).contains(selection)&&Arrays.asList(employeeColumns).contains(column)) {
            try {
                PreparedStatement ps = employeesDAO.connectToDatabase().prepareStatement(selectEmployeesUsingParameters);
                ps.setString(1, selection);
                ps.setString(2, column);
                ps.setString(3, variable);
                ResultSet resultSet = ps.executeQuery();

                if (resultSet != null) {
                    while (resultSet.next()) {
                        if (selection.equals("EmpID") || selection.equals("Salary")){
                            Printer.printMessage(selection + ":: " + resultSet.getInt(1));
                        } else if (selection.equals("JoinDate")|| selection.equals("DateOfBirth")){
                            Printer.printMessage(selection + ":: " + resultSet.getDate(1));
                        } else {
                            Printer.printMessage((selection + ":: " + resultSet.getString(1)));
                        }
                    }
                } else {
                    Printer.printMessage("No data exists!!");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            if(!Arrays.asList(employeeColumns).contains(selection)){
                Printer.printMessage("The selection - "+ selection+ " - is invalid.");
            }
            if(!Arrays.asList(employeeColumns).contains(column)){
                Printer.printMessage("The column - "+ column + " - is invalid.");
            }
        }
    }

    public void viewEmployee(String column, String variable){
        if(Arrays.asList(employeeColumns).contains(column)) {
            try {
                PreparedStatement ps = employeesDAO.connectToDatabase().prepareStatement(selectEmployeesUsingParameters);
                ps.setString(1, "*");
                ps.setString(2, column);
                ps.setString(3, variable);
                ResultSet resultSet = ps.executeQuery();

                if (resultSet != null) {
                    while (resultSet.next()) {
                        Printer.printMessage("EmpId:: " + resultSet.getInt(1));
                        Printer.printMessage(("Title:: " + resultSet.getString(2)));
                        Printer.printMessage(("First Name:: " + resultSet.getString(3)));
                        Printer.printMessage(("Middle Initial:: " + resultSet.getString(4)));
                        Printer.printMessage(("Last Name:: " + resultSet.getString(5)));
                        Printer.printMessage(("Gender:: " + resultSet.getString(6)));
                        Printer.printMessage(("Email:: " + resultSet.getString(7)));
                        Printer.printMessage(("DateOfBirth:: " + resultSet.getDate(8)));
                        Printer.printMessage(("JoinDate:: " + resultSet.getDate(9)));
                        Printer.printMessage(("Salary:: " + resultSet.getInt(10)));
                    }
                } else {
                    Printer.printMessage("No data exists!!");
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else { Printer.printMessage("The column - "+ column + " - is invalid."); }
    }

    public void updateEmployee(String updateSelection, String updatedVariable, String column, String columnUpdate){
        if(Arrays.asList(employeeColumns).contains(updateSelection) &&Arrays.asList(employeeColumns).contains(column)) {
            try {
                PreparedStatement ps = employeesDAO.connectToDatabase().prepareStatement(updateEmployeeUsingParameters);
                ps.setString(1, updateSelection);

                if (updateSelection.equals("EmpID") || updateSelection.equals("Salary")) {
                    ps.setInt(2, Integer.parseInt(updatedVariable));
                } else if (updateSelection.equals("JoinDate") || updateSelection.equals("DateOfBirth")) {
                    ps.setDate(2, Employees.parseDate(updatedVariable));
                } else {
                    ps.setString(2, updatedVariable);
                }
                ps.setString(3, column);
                ps.setString(4, columnUpdate);

                int hasRun = ps.executeUpdate();
                if (hasRun == 1) {
                    Printer.printMessage(updateSelection + " updated to " + updatedVariable + ".");
                } else {
                    Printer.printMessage("Update seems to have failed...");
                }
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }

        } else{
            if(!Arrays.asList(employeeColumns).contains(updateSelection)){
                Printer.printMessage("The update selection - "+ updateSelection + " - is invalid.");
            }
            if(!Arrays.asList(employeeColumns).contains(column)){
                Printer.printMessage("The column - " + column + " - is invalid.");
            }
        }
    }

    public void insertEmployee(int empId, String title, String firstName, String middleInitial, String lastName, char gender,
                               String email, String dob, String joinDate, int salary){
        try {
            PreparedStatement ps = employeesDAO.connectToDatabase().prepareStatement(updateEmployeeUsingParameters);
            ps.setInt(1, empId);
            ps.setString(2, title);
            ps.setString(3, firstName);
            ps.setString(4, middleInitial);
            ps.setString(5, lastName);
            ps.setString(6, String.valueOf(gender));
            ps.setString(7, email);
            ps.setDate(8, Employees.parseDate(dob));
            ps.setDate(9, Employees.parseDate(joinDate));
            ps.setInt(10, salary);

            int hasRun = ps.executeUpdate();
            if (hasRun == 1) {
                Printer.printMessage("Employee has been added to database");
                EmployeeManager.newEmployee(empId, title, firstName, middleInitial, lastName, gender, email, dob,
                        joinDate, salary);
            } else {
                Printer.printMessage("Insert seems to have failed...");
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    public void removeEmployee(String column, String variable){
        if(Arrays.asList(employeeColumns).contains(column)){
            try {
                PreparedStatement ps = employeesDAO.connectToDatabase().prepareStatement(removeEmployee);
                ps.setString(1, column);

                if (column.equals("EmpID") || column.equals("Salary")) {
                    ps.setInt(2, Integer.parseInt(variable));
                } else if (column.equals("JoinDate") || column.equals("DateOfBirth")) {
                    ps.setDate(2, Employees.parseDate(variable));
                } else {
                    ps.setString(2, variable);
                }

                int hasRun = ps.executeUpdate();
                if (hasRun == 1) {
                    Printer.printMessage("Employee has been removed.");
                } else {
                    Printer.printMessage("Removal seems to have failed...");
                }
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        } else {
            Printer.printMessage("The column name - " + column + " - is invalid.");
        }
    }

    @Override
    public void close() throws Exception {

    }
}
