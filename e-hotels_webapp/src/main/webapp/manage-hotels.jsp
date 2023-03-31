<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="com.example.Hotel" %>
<%@ page import="com.example.HotelService" %>
<%@ page import="java.util.*" %>

<%
    HotelService hotelService = new HotelService();
    List<Hotel> allHotels = hotelService.getHotels();
%>
<!DOCTYPE html>
<html lang="en">
<html>
    <head>
        <title>E-Hotels - Manage Hotels</title>
    </head>
    <body>
        <a href="admin-view.jsp"><input type="button" value="Back to Admin View"></a>
        <center>
            <br>
            <h1>E-Hotels Hotel Manager</h1>
            <h2>Add/Update Hotel</h2>
            <form action="hotel-manager-servlet" method="get">
                <label for="chain">Chain:</label>
                <input type="text" name="chain" id="chain" size="8"> &emsp;
                <label for="hotel_id">Hotel ID:</label>
                <input type="text" name="hotel_id" id="hotel_id" size="8"><br><br>

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

                <label for="rating">Rating (number of stars):</label>
                <input type="text" name="rating" id="rating" size="8"><br><br>

                <label for="email">Email Address:</label>
                <input type="text" name="email" id="email" size="8"> &emsp;
                <label for="phones">Phone Numbers:</label>
                <input type="text" name="phones" id="phones"><br><br>

                <label for="mgr_sin_ssn">Manager SIN/SSN:</label>
                <input type="text" name="mgr_sin_ssn" id="mgr_sin_ssn" size="8"><br><br>

                <input type="submit" value="Add/Update Hotel">
            </form>
            <h2>Delete Hotel</h2>
            <form action="hotel-manager-servlet" method="post">
                <label for="del_chain">Chain:</label>
                <input type="text" name="del_chain" id="del_chain" size="8"> &emsp;
                <label for="del_hotel_id">Hotel ID:</label>
                <input type="text" name="del_hotel_id" id="del_hotel_id" size="8"><br><br>
                <input type="submit" value="Delete Hotel">
            </form>

        </center>
        <h2>Current Hotels (<%=allHotels.size()%> Results)</h2>
        <% for (Hotel h : allHotels) {%>
            <%=h.toString()%>
        <% } %><br><br>
    </body>
</html>
