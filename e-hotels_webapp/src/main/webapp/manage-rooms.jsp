<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="com.example.Room" %>
<%@ page import="com.example.RoomService" %>
<%@ page import="java.util.*" %>

<%
    RoomService roomService = new RoomService();
    List<Room> allRooms = roomService.getRooms();
%>
<!DOCTYPE html>
<html lang="en">
<html>
    <head>
        <title>E-Hotels - Manage Rooms</title>
    </head>
    <body>
        <a href="admin-view.jsp"><input type="button" value="Back to Admin View"></a>
        <center>
            <br>
            <h1>E-Hotels Room Manager</h1>
            <h2>Add/Update Room</h2>
            <form action="room-manager-servlet" method="get">
                <label for="chain">Chain:</label>
                <input type="text" name="chain" id="chain" size="8"> &emsp;
                <label for="hotel_id">Hotel ID:</label>
                <input type="text" name="hotel_id" id="hotel_id" size="8"> &emsp;
                <label for="number">Room Number:</label>
                <input type="text" name="number" id="number" size="8"> &emsp;
                <label for="floor">Floor:</label>
                <input type="text" name="floor" id="floor" size="8"><br><br>

                <label for="price">Price:</label>
                <input type="text" name="price" id="price" size="8"><br><br>

                <label for="capacity">Capacity:</label>
                <select name="capacity" id="capacity">
                    <% for (int i = 1; i <= 10; i++) { %>
                            <option><%=i%></option>
                       <% } %>
                </select> &emsp;
                <label for="extends">Can be Extended:</label>
                <input type="checkbox" name="extends" id="extends"> &emsp;
                <label for="view">View Type:</label>
                <select name="view" id="view">
                    <option>Sea</option>
                    <option>Mountain</option>
                </select><br><br>

                <label for="amenities">Amenities:</label>
                <input type="text" name="amenities" id="amenities"> &emsp;
                <label for="problems">Problems/Damages:</label>
                <input type="text" name="problems" id="problems""><br><br>

                <input type="submit" value="Add/Update Room">
            </form>
            <h2>Delete Room</h2>
            <form action="room-manager-servlet" method="post">
                <label for="del_chain">Chain:</label>
                <input type="text" name="del_chain" id="del_chain" size="8"> &emsp;
                <label for="del_hotel_id">Hotel ID:</label>
                <input type="text" name="del_hotel_id" id="del_hotel_id" size="8"> &emsp;
                <label for="del_number">Room Number:</label>
                <input type="text" name="del_number" id="del_number" size="8"> &emsp;
                <label for="del_floor">Floor:</label>
                <input type="text" name="del_floor" id="del_floor" size="8"><br><br>
                <input type="submit" value="Delete Room">
            </form>

        </center>
        <h2>Current Rooms (<%=allRooms.size()%> Results)</h2>
        <% for (Room r : allRooms) {%>
            <%=r.toString()%>
        <% } %><br><br>
    </body>
</html>
