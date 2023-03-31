<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="com.example.Customer" %>
<%@ page import="com.example.CustomerService" %>
<%@ page import="java.util.*" %>

<%
    CustomerService customerService = new CustomerService();
    List<Customer> allCustomers = customerService.getCustomers();
%>
<!DOCTYPE html>
<html lang="en">
<html>
    <head>
        <title>E-Hotels - Manage Customers</title>
    </head>
    <body>
        <a href="admin-view.jsp"><input type="button" value="Back to Admin View"></a>
        <center>
            <br>
            <h1>E-Hotels Customer Manager</h1>
            <h2>Add/Update Customer</h2>
            <form action="customer-manager-servlet" method="get">
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
                <input type="text" name="country" id="country" size="8">&emsp;
                <label for="zip_postal_code">ZIP/Postal Code:</label>
                <input type="text" name="zip_postal_code" id="zip_postal_code" size="8"><br><br>

                <label for="chains">Chains to Register:</label>
                <input type="text" name="chains" id="chains"><br><br>

                <input type="submit" value="Add/Update Customer">
            </form>
            <h2>Delete Customer</h2>
            <form action="customer-manager-servlet" method="post">
                <label for="del_sin_ssn">SIN/SSN:</label>
                <input type="text" name="del_sin_ssn" id="del_sin_ssn" size="8"><br><br>
                <input type="submit" value="Delete Customer">
            </form>

        </center>
        <h2>Current Customers (<%=allCustomers.size()%> Results)</h2>
        <% for (Customer c : allCustomers) {%>
            <%=c.toString()%>
        <% } %><br><br>
    </body>
</html>
