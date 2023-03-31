package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class CustomerManagerServlet extends HttpServlet {

        public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try {
                String first_name = request.getParameter("first_name");
                String middle_name = request.getParameter("middle_name");
                String last_name = request.getParameter("last_name");
                String sin_ssn = request.getParameter("sin_ssn");
                String street_address = request.getParameter("street_address");
                String city = request.getParameter("city");
                String province_state = request.getParameter("province_state");
                String country = request.getParameter("country");
                String zip_postal_code = request.getParameter("zip_postal_code");
                String chains = request.getParameter("chains");

                List<String> chainNames = Arrays.asList(chains.split("[,; ]"));
                Map<String, String> regMap = new HashMap<>();
                for (String s : chainNames) {
                    regMap.put(s, LocalDate.now().toString());
                }
                CustomerService customerService = new CustomerService();
                Customer newCustomer = new Customer(first_name, middle_name, last_name, sin_ssn,
                        new Address(street_address, city, province_state, country, zip_postal_code), regMap);

                List<Customer> allCustomers = customerService.getCustomers();
                List<String> allCustomersSin = new ArrayList<>();
                for (Customer c : allCustomers) {
                    allCustomersSin.add(c.getCustomerSinSsn());
                }

                String test;
                if (allCustomersSin.contains(sin_ssn)) {
                    test = customerService.updateCustomer(newCustomer);
                } else {
                    test = customerService.createCustomer(newCustomer);
                }

                System.out.println(test);
                if (test.startsWith("Error")) {
                    throw new Exception(test);
                }
                response.sendRedirect("index.jsp");
            } catch (Exception e) {
                System.out.println(e);
                response.sendRedirect("manage-customers.jsp");
            }
        }

        public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try {
                String sin_ssn = request.getParameter("del_sin_ssn");

                CustomerService customerService = new CustomerService();
                Customer newCustomer = new Customer(null, null, null, sin_ssn, null, null);
                String test = customerService.deleteCustomer(newCustomer);
                System.out.println(test);
                if (test.startsWith("Error")) {
                    throw new Exception(test);
                }
                response.sendRedirect("index.jsp");
            } catch (Exception e) {
                System.out.println(e);
                response.sendRedirect("manage-customers.jsp");
            }
        }
}
