<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="com.example.Chain" %>
<%@ page import="com.example.ChainService" %>
<%@ page import="com.example.Booking" %>
<%@ page import="com.example.BookingService" %>
<%@ page import="com.example.Renting" %>
<%@ page import="com.example.RentingService" %>

<%@ page import="java.util.*" %>

<%
    ChainService chainService = new ChainService();
    List<Chain> allChains = chainService.getChains();

    BookingService bookingService = new BookingService();
    List<Booking> allBookings = bookingService.getBookings();

    RentingService rentingService = new RentingService();
    List<Renting> allRentings = rentingService.getRentings();
%>

<!DOCTYPE html>
<html lang="en">
<html>
    <head>
        <title>E-Hotels - Employee View</title>
    </head>
    <body>
        <a href="index.jsp"><input type="button" value="Back Home"></a>
        <center>
            <br>
            <h1>E-Hotels Employee Create Renting</h1><br>
            <h2>Create a Renting</h2><br>
            <form action="employee-servlet" method="post">
                <label for="rent_chain_name">Chain:</label>
                <select name="rent_chain_name" id="rent_chain_name">
                    <% for (Chain c : allChains) { %>
                        <option><%=c.getChainName()%></option>
                        <% } %>
                </select> &emsp;
                <label for="rent_hotel_id">Hotel ID:</label>
                <input type="text" name="rent_hotel_id" id="rent_hotel_id" size="8"> &emsp;
                <label for="rent_room_number">Room Number:</label>
                <input type="text" name="rent_room_number" id="rent_room_number" size="8"> &emsp;
                <label for="rent_room_floor">Floor:</label>
                <input type="text" name = "rent_room_floor" id="rent_room_floor" size="8"><br><br>
                <label for="rent_start_date">Start Date:</label>
                <input type="text" name = "rent_start_date" id="rent_start_date" size="8"> &emsp;
                <label for="rent_end_date">End Date:</label>
                <input type="text" name = "rent_end_date" id="rent_end_date" size="8"> &emsp;
                <label for="rent_sin">SIN/SSN:</label>
                <input type="text" name = "rent_sin" id="rent_sin" size="8"><br><br>
                <button type="submit">Create Renting or Convert Booking</button><br><br>
            </form>
        </center>
        <h2>Current Bookings (<%=allBookings.size()%> Results)</h2>
        <% for (Booking b : allBookings) {%>
            <%=b.toString()%>
        <% } %><br><br>
        <h2>Current Rentings (<%=allRentings.size()%> Results)</h2>
        <% for (Renting r : allRentings) {%>
            <%=r.toString()%>
        <% } %>
    </body>
</html>
