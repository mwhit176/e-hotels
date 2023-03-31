package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

public class EmployeeManagerServlet extends HttpServlet {

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
                String role_position = request.getParameter("role_position");
                String chain = request.getParameter("chain");
                String hotel_id = request.getParameter("hotel_id");

                EmployeeService employeeService = new EmployeeService();
                Employee newEmployee = new Employee(first_name, middle_name, last_name, sin_ssn,
                        new Address(street_address, city, province_state, country, zip_postal_code), role_position, chain, hotel_id);

                List<Employee> allEmployees = employeeService.getEmployees();
                List<String> allEmployeesSin = new ArrayList<>();
                for (Employee e : allEmployees) {
                    allEmployeesSin.add(e.getEmpSinSsn());
                }

                String test;
                if (allEmployeesSin.contains(sin_ssn)) {
                    test = employeeService.updateEmployee(newEmployee);
                } else {
                    test = employeeService.createEmployee(newEmployee);
                }

                System.out.println(test);
                if (test.startsWith("Error")) {
                    throw new Exception(test);
                }
                response.sendRedirect("index.jsp");
            } catch (Exception e) {
                System.out.println(e);
                response.sendRedirect("manage-employees.jsp");
            }
        }

        public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try {
                String sin_ssn = request.getParameter("del_sin_ssn");

                EmployeeService employeeService = new EmployeeService();
                Employee newEmployee = new Employee(null, null, null, sin_ssn, null, null, null, null);
                String test = employeeService.deleteEmployee(newEmployee);
                System.out.println(test);
                if (test.startsWith("Error")) {
                    throw new Exception(test);
                }
                response.sendRedirect("index.jsp");
            } catch (Exception e) {
                System.out.println(e);
                response.sendRedirect("manage-employees.jsp");
            }
        }
}
