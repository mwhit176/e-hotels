<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="com.example.SpecialViewService" %>
<%@ page import="java.util.*" %>

<%
    SpecialViewService svc = new SpecialViewService();
    List<List<String>> roomsPerArea = svc.getNumRoomsPerArea();
    List<List<String>> hotelRoomCapacity = svc.getHotelRoomCapacity();
%>
<!DOCTYPE html>
<html lang="en">
<html>
    <head>
        <title>E-Hotels - Special View</title>
    </head>
    <body>
        <a href="index.jsp"><input type="button" value="Back Home"></a>
        <center>
            <br>
            <h1>E-Hotels Special Views</h1><br>
        </center>
        <h2>Number of Rooms per Area</h2><br>
        <table>
            <tr>
                <th></th>
                <th>City</th>
                <th>Province/State</th>
                <th>Country</th>
                <th>Number of Rooms</th>
            </tr>
            <% for (List<String> row : roomsPerArea) {%>
                <tr>
                    <% for (String col : row) {%>
                        <td><%=col%></td>
                    <%}%>
                </tr>
            <%}%>
        </table><br><br>
        <h2>Room Capacities for Wyndham Hotel 000000001</h2><br>
        <table>
            <tr>
                <th></th>
                <th>Room Number</th>
                <th>Floor</th>
                <th>Capacity</th>
            </tr>
            <% for (List<String> row : hotelRoomCapacity) {%>
                <tr>
                    <% for (String col : row) {%>
                        <td><%=col%></td>
                    <%}%>
                </tr>
            <%}%>
        </table>
    </body>
</html>
