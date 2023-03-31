package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeeService {

    public List<Employee> getEmployees() throws Exception {
        String employeeQuery = "SELECT * FROM employee";
        ConnectionDB db = new ConnectionDB();

        List<Employee> employees = new ArrayList<>();

        try {
            Connection con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(employeeQuery);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String first_name = rs.getString("first_name");
                String middle_name = rs.getString("middle_name");
                String last_name = rs.getString("last_name");
                String emp_sin_ssn = rs.getString("emp_sin_ssn");
                String street_address = rs.getString("street_address");
                String city = rs.getString("city");
                String province_state = rs.getString("province_state");
                String country = rs.getString("country");
                String zip_postal_code = rs.getString("zip_postal_code");
                Address address = new Address(street_address, city, province_state, country, zip_postal_code);
                String role_position = rs.getString("role_position");
                String chain_name = rs.getString("chain_name");
                String hotel_id = rs.getString("hotel_id");

                Employee curEmployee = new Employee(first_name, middle_name, last_name, emp_sin_ssn, address, role_position, chain_name, hotel_id);
                employees.add(curEmployee);
            }
            rs.close();
            stmt.close();
            con.close();
            db.close();
            return employees;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String createEmployee(Employee employee) throws Exception {
        String message = "";
        Connection con = null;
        ConnectionDB db = new ConnectionDB();
        String insertEmployeeQuery = "INSERT INTO employee VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(insertEmployeeQuery);
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getMiddleName());
            stmt.setString(3, employee.getLastName());
            stmt.setString(4, employee.getEmpSinSsn());
            stmt.setString(5, employee.getAddress().getStreetAddress());
            stmt.setString(6, employee.getAddress().getCity());
            stmt.setString(7, employee.getAddress().getProvinceState());
            stmt.setString(8, employee.getAddress().getCountry());
            stmt.setString(9, employee.getAddress().getZipPostalCode());
            stmt.setString(10, employee.getRolePosition());
            stmt.setString(11, employee.getChainName());
            stmt.setString(12, employee.getHotelID());
            int output = stmt.executeUpdate();
            System.out.println(output);
            stmt.close();
            db.close();
        } catch (Exception e) {
            message = "Error while inserting employee: " + e.getMessage();
        } finally {
            if (con != null) {
                con.close();
            }
            if (message.equals("")) {
                message = "Employee successfully inserted!";
            }
        }
        return message;
    }

    // The attributes of the primary key (SSN in this case) will be immutable.
    // However, everything else can be updated.
    public String updateEmployee(Employee employee) throws Exception {
        Connection con = null;
        String message = "";
        String updateEmployeeQuery = "UPDATE employee SET " +
                "first_name = ?, " +
                "middle_name = ?, " +
                "last_name = ?, " +
                "street_address = ?, " +
                "city = ?, " +
                "province_state = ?, " +
                "country = ?, " +
                "zip_postal_code = ?, " +
                "role_position = ?, " +
                "chain_name = ?, " +
                "hotel_id = ? " +
                "WHERE emp_sin_ssn = ?";
        ConnectionDB db = new ConnectionDB();
        try {
            con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(updateEmployeeQuery);
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getMiddleName());
            stmt.setString(3, employee.getLastName());
            stmt.setString(4, employee.getAddress().getStreetAddress());
            stmt.setString(5, employee.getAddress().getCity());
            stmt.setString(6, employee.getAddress().getProvinceState());
            stmt.setString(7, employee.getAddress().getCountry());
            stmt.setString(8, employee.getAddress().getZipPostalCode());
            stmt.setString(9, employee.getRolePosition());
            stmt.setString(10, employee.getChainName());
            stmt.setString(11, employee.getHotelID());
            stmt.setString(12, employee.getEmpSinSsn());
            int output = stmt.executeUpdate();
            System.out.println(output);
            stmt.close();
            db.close();
        } catch (Exception e) {
            message = "Error while updating employee: " + e.getMessage();
        } finally {
            if (con != null) {
                con.close();
            }
            if (message.equals("")) {
                message = "Employee successfully updated!";
            }
        }
        return message;
    }

    // Theoretically, the only value of Employee that's necessary is the SIN.
    // But this is not the case for other tables, which may have compound primary keys.
    // For consistency's sake we just use the Employee class.
    public String deleteEmployee(Employee employee) throws Exception {
        Connection con = null;
        String message = "";
        String deleteEmployeeQuery = "DELETE FROM employee WHERE emp_sin_ssn = ?";
        ConnectionDB db = new ConnectionDB();
        try {
            con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(deleteEmployeeQuery);
            stmt.setString(1, employee.getEmpSinSsn());
            int output = stmt.executeUpdate();
            System.out.println(output);
            stmt.close();
            db.close();
        } catch (Exception e) {
            message = "Error while deleting employee: " + e.getMessage();
        } finally {
            if (con != null) {
                con.close();
            }
            if (message.equals("")) {
                message = "Employee successfully deleted!";
            }
        }
        return message;
    }
}
