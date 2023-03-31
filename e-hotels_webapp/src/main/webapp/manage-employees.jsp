<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="com.example.Employee" %>
<%@ page import="com.example.EmployeeService" %>
<%@ page import="java.util.*" %>

<%
    EmployeeService employeeService = new EmployeeService();
    List<Employee> allEmployees = employeeService.getEmployees();
%>
<!DOCTYPE html>
<html lang="en">
<html>
    <head>
        <title>E-Hotels - Manage Employees</title>
    </head>
    <body>
        <a href="admin-view.jsp"><input type="button" value="Back to Admin View"></a>
        <center>
            <br>
            <h1>E-Hotels Employee Manager</h1>
            <h2>Add/Update Employee</h2>
            <form action="employee-manager-servlet" method="get">
                <label for="first_name">First Name:</label>
                <input type="text" name="first_name" id="first_name" size="8"> &emsp;
                <label for="middle_name">Middle Name:</label>
                <input type="text" name="middle_name" id="middle_name" size="8"> &emsp;
                <label for="last_name">Last Name:</label>
                <input type="text" name="last_name" id="last_name" size="8"> &emsp;
                <label for="sin_ssn">SIN/SSN:</label>
                <input type="text" name="sin_ssn" id="sin_ssn" size="8"><br><br>

                <label for="street_address">Street Address:</label>
                <input type="text" name="street_address" id="street_address" size="8"> &emsp;
                <label for="city">City:</label>
                <input type="text" name="city" id="city" size="8"> &emsp;
                <label for="province_state">Province/State:</label>
                <input type="text" name="province_state" id="province_state" size="8"> &emsp;
                <label for="country">Country:</label>
                <input type="text" name="country" id="country" size="8"> &emsp;
                <label for="zip_postal_code">ZIP/Postal Code:</label>
                <input type="text" name="zip_postal_code" id="zip_postal_code" size="8"><br><br>

                <label for="role_position">Role/Position:</label>
                <input type="text" name="role_position" id="role_position" size="8"> &emsp;
                <label for="chain">Chain:</label>
                <input type="text" name="chain" id="chain" size="8"> &emsp;
                <label for="hotel_id">Hotel ID:</label>
                <input type="text" name="hotel_id" id="hotel_id" size="8"><br><br>

                <input type="submit" value="Add/Update Employee">
            </form>
            <h2>Delete Employee</h2>
            <form action="employee-manager-servlet" method="post">
                <label for="del_sin_ssn">SIN/SSN:</label>
                <input type="text" name="del_sin_ssn" id="del_sin_ssn" size="8"><br><br>
                <input type="submit" value="Delete Employee">
            </form>

        </center>
        <h2>Current Employees (<%=allEmployees.size()%> Results)</h2>
        <% for (Employee e : allEmployees) {%>
            <%=e.toString()%>
        <% } %><br><br>
    </body>
</html>
