package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomerService {

    public List<Customer> getCustomers() throws Exception {
        String customerOnlyQuery = "SELECT * FROM customer";
        String customerRegistrationQuery = "SELECT * FROM registration";
        ConnectionDB db = new ConnectionDB();

        List<Customer> customers = new ArrayList<>();

        try {
            Connection con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(customerOnlyQuery);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String first_name = rs.getString("first_name");
                String middle_name = rs.getString("middle_name");
                String last_name = rs.getString("last_name");
                String customer_sin_ssn = rs.getString("customer_sin_ssn");
                String street_address = rs.getString("street_address");
                String city = rs.getString("city");
                String province_state = rs.getString("province_state");
                String country = rs.getString("country");
                String zip_postal_code = rs.getString("zip_postal_code");
                Address address = new Address(street_address, city, province_state, country, zip_postal_code);

                Customer curCustomer = new Customer(first_name, middle_name, last_name, customer_sin_ssn, address, new HashMap<>());
                customers.add(curCustomer);
            }
            stmt = con.prepareStatement(customerRegistrationQuery);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String chain_name = rs.getString("chain_name");
                String customer_sin_ssn = rs.getString("customer_sin_ssn");
                String registration_date = rs.getString("registration_date");
                for (Customer c : customers) {
                    if (c.getCustomerSinSsn().equals(customer_sin_ssn)) {
                        Map<String, String> curRegistrations = c.getRegistrations();
                        curRegistrations.put(chain_name, registration_date);
                        c.setRegistrations(curRegistrations);
                    }
                }
            }
            rs.close();
            stmt.close();
            con.close();
            db.close();
            return customers;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String createCustomer(Customer customer) throws Exception {
        String message = "";
        Connection con = null;
        ConnectionDB db = new ConnectionDB();
        String insertCustomerQuery = "INSERT INTO customer VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String insertRegistrationQuery = "INSERT INTO registration VALUES (?, ?, CAST(? AS DATE))";
        try {
            con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(insertCustomerQuery);
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getMiddleName());
            stmt.setString(3, customer.getLastName());
            stmt.setString(4, customer.getCustomerSinSsn());
            stmt.setString(5, customer.getAddress().getStreetAddress());
            stmt.setString(6, customer.getAddress().getCity());
            stmt.setString(7, customer.getAddress().getProvinceState());
            stmt.setString(8, customer.getAddress().getCountry());
            stmt.setString(9, customer.getAddress().getZipPostalCode());
            int output = stmt.executeUpdate();
            Map<String, String> registrations = customer.getRegistrations();
            for (String chain : registrations.keySet()) {
                stmt = con.prepareStatement(insertRegistrationQuery);
                stmt.setString(1, chain);
                stmt.setString(2, customer.getCustomerSinSsn());
                stmt.setString(3, registrations.get(chain));
                output += stmt.executeUpdate();
            }
            System.out.println(output);
            stmt.close();
            db.close();
        } catch (Exception e) {
            message = "Error while inserting customer: " + e.getMessage();
        } finally {
            if (con != null) {
                con.close();
            }
            if (message.equals("")) {
                message = "Customer successfully inserted!";
            }
        }
        return message;
    }

    public String updateCustomer(Customer customer) throws Exception {
        Connection con = null;
        String message = "";
        String updateCustomerQuery = "UPDATE customer SET " +
                "first_name = ?, " +
                "middle_name = ?, " +
                "last_name = ?, " +
                "street_address = ?, " +
                "city = ?, " +
                "province_state = ?, " +
                "country = ?, " +
                "zip_postal_code = ? " +
                "WHERE customer_sin_ssn = ?";
        ConnectionDB db = new ConnectionDB();
        try {
            con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(updateCustomerQuery);
            stmt.setString(1, customer.getFirstName());
            stmt.setString(2, customer.getMiddleName());
            stmt.setString(3, customer.getLastName());
            stmt.setString(4, customer.getAddress().getStreetAddress());
            stmt.setString(5, customer.getAddress().getCity());
            stmt.setString(6, customer.getAddress().getProvinceState());
            stmt.setString(7, customer.getAddress().getCountry());
            stmt.setString(8, customer.getAddress().getZipPostalCode());
            stmt.setString(9, customer.getCustomerSinSsn());
            int output = stmt.executeUpdate();
            System.out.println(output);
            stmt.close();
            db.close();
        } catch (Exception e) {
            message = "Error while updating customer: " + e.getMessage();
        } finally {
            if (con != null) {
                con.close();
            }
            if (message.equals("")) {
                message = "Customer successfully updated!";
            }
        }
        return message;
    }

    public String deleteCustomer(Customer customer) throws Exception {
        Connection con = null;
        String message = "";
        String deleteCustomerQuery = "DELETE FROM customer WHERE customer_sin_ssn = ?";
        ConnectionDB db = new ConnectionDB();
        try {
            con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(deleteCustomerQuery);
            stmt.setString(1, customer.getCustomerSinSsn());
            int output = stmt.executeUpdate();
            System.out.println(output);
            stmt.close();
            db.close();
        } catch (Exception e) {
            message = "Error while deleting customer: " + e.getMessage();
        } finally {
            if (con != null) {
                con.close();
            }
            if (message.equals("")) {
                message = "Customer successfully deleted!";
            }
        }
        return message;
    }
}
