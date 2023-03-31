<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ page import="com.example.Chain" %>
<%@ page import="com.example.ChainService" %>
<%@ page import="com.example.Hotel" %>
<%@ page import="com.example.HotelService" %>
<%@ page import="com.example.Room" %>
<%@ page import="com.example.RoomService" %>
<%@ page import="com.example.PruneUtil" %>

<%@ page import="java.util.*" %>

<%
    ChainService chainService = new ChainService();
    List<Chain> allChains = chainService.getChains();

    HotelService hotelService = new HotelService();
    List<Hotel> allHotels = hotelService.getHotels();
    Set<String> allCities = new HashSet<>();
    for (Hotel h : allHotels) {
        allCities.add(h.getAddress().getCity());
    }

    RoomService roomService = new RoomService();
    List<Room> allRooms = roomService.getRooms();

    String startDate = request.getParameter("start_date");
    String endDate = request.getParameter("end_date");
    String scapacityLowerBound = request.getParameter("min_capacity");
    String scapacityUpperBound = request.getParameter("max_capacity");
    int capacityLowerBound;
    if (scapacityLowerBound == null) {
        capacityLowerBound = 1;
    } else {
        capacityLowerBound = Integer.parseInt(scapacityLowerBound);
    }
    int capacityUpperBound;
    if (scapacityUpperBound == null) {
        capacityUpperBound = 10;
    } else {
        capacityUpperBound = Integer.parseInt(scapacityUpperBound);
    }
    String pcity = request.getParameter("city");
    String pchain = request.getParameter("chain");
    String snumStarsLowerBound = request.getParameter("min_stars");
    String snumStarsUpperBound = request.getParameter("max_stars");
    int numStarsLowerBound;
    if (snumStarsLowerBound == null) {
        numStarsLowerBound = 1;
    } else {
        numStarsLowerBound = Integer.parseInt(snumStarsLowerBound);
    }
    int numStarsUpperBound;
    if (snumStarsUpperBound == null) {
        numStarsUpperBound = 5;
    } else {
        numStarsUpperBound = Integer.parseInt(snumStarsUpperBound);
    }
    String spriceLowerBound = request.getParameter("min_price");
    String spriceUpperBound = request.getParameter("max_price");
    double priceLowerBound;
    if (spriceLowerBound == null) {
        priceLowerBound = 0.00;
    } else {
        priceLowerBound = Double.parseDouble(spriceLowerBound);
    }
    double priceUpperBound;
    if (spriceUpperBound == null) {
        priceUpperBound = 10000.0;
    } else {
        priceUpperBound = Double.parseDouble(spriceUpperBound);
    }

    allRooms = PruneUtil.pruneRooms(allRooms,
                                    startDate, endDate,
                                    capacityLowerBound, capacityUpperBound,
                                    pcity, pchain,
                                    numStarsLowerBound, numStarsUpperBound,
                                    priceLowerBound, priceUpperBound);

%>

<!DOCTYPE html>
<html lang="en">
<html>
    <head>
        <title>E-Hotels - Customer View</title>
    </head>
    <body>
        <a href="index.jsp"><input type="button" value="Back Home"></a>
        <center>
            <br>
            <h1>E-Hotels Customer Create Booking</h1><br>
            <h2>Create a Booking</h2><br>
            <form action="customer-servlet" method="post">
                <label for="book_chain_name">Chain:</label>
                <select name="book_chain_name" id="book_chain_name">
                    <% for (Chain c : allChains) { %>
                        <option><%=c.getChainName()%></option>
                        <% } %>
                </select> &emsp;
                <label for="book_hotel_id">Hotel ID:</label>
                <input type="text" name="book_hotel_id" id="book_hotel_id" size="8"> &emsp;
                <label for="book_room_number">Room Number:</label>
                <input type="text" name="book_room_number" id="book_room_number" size="8"> &emsp;
                <label for="book_room_floor">Floor:</label>
                <input type="text" name = "book_room_floor" id="book_room_floor" size="8"><br><br>
                <label for="book_start_date">Start Date:</label>
                <input type="text" name = "book_start_date" id="book_start_date" size="8"> &emsp;
                <label for="book_end_date">End Date:</label>
                <input type="text" name = "book_end_date" id="book_end_date" size="8"> &emsp;
                <label for="book_sin">SIN/SSN:</label>
                <input type="text" name = "book_sin" id="book_sin" size="8"><br><br>
                <button type="submit">Create Booking</button><br><br>
            </form>

            <h2>Filter and View Available Rooms</h2><br>
            <form action="customer-view.jsp" method="post">
                <label for="start_date">Start Date:</label>
                <input type="text" name = "start_date" id="start_date" value="<%=startDate != null ? startDate : "1000-01-01" %>" size="8"> &emsp;
                <label for="end_date">End Date:</label>
                <input type="text" name = "end_date" id="end_date" value="<%=endDate != null ? endDate : "1000-01-01" %>" size="8"><br><br>
                <label for="min_capacity">Min Capacity:</label>
                <select name="min_capacity" id="min_capacity">
                    <% for (int i = 1; i <= 10; i++) {
                        if (i == capacityLowerBound) {%>
                            <option selected><%=i%></option>
                        <%} else {%>
                            <option><%=i%></option>
                        <%}
                       }%>
                </select> &emsp;
                <label for="max_capacity">Max Capacity:</label>
                <select name="max_capacity" id="max_capacity">
                    <% for (int i = 1; i <= 10; i++) {
                        if (i == capacityUpperBound) {%>
                            <option selected><%=i%></option>
                        <%} else {%>
                            <option><%=i%></option>
                        <%}
                       }%>
                </select><br><br>
                <label for="city">City:</label>
                <select name="city" id="city">
                    <option value="">Any</option>
                    <% for (String city : allCities) {
                        if (pcity != null && pcity.equals(city)) { %>
                            <option selected><%=city%></option>
                        <% } else { %>
                            <option><%=city%></option>
                        <% }
                    } %>
                </select> &emsp;
                <label for="chain">Chain:</label>
                <select name="chain" id="chain">
                    <option value="">Any</option>
                    <% for (Chain c : allChains) {
                        if (pchain != null && pchain.equals(c.getChainName())) { %>
                            <option selected><%=c.getChainName()%></option>
                        <% } else { %>
                            <option><%=c.getChainName()%></option>
                        <% }
                    } %>
                </select><br><br>
                <label for="min_stars">Min Stars:</label>
                <select name="min_stars" id="min_stars">
                    <% for (int i = 1; i <= 5; i++) {
                        if (i == numStarsLowerBound) {%>
                            <option selected><%=i%></option>
                        <%} else {%>
                            <option><%=i%></option>
                        <%}
                       }%>
                </select> &emsp;
                <label for="max_stars">Max Stars:</label>
                <select name="max_stars" id="max_stars">
                    <% for (int i = 1; i <= 5; i++) {
                        if (i == numStarsUpperBound) {%>
                            <option selected><%=i%></option>
                        <%} else {%>
                            <option><%=i%></option>
                        <%}
                       }%>
                </select><br><br>
                <label for="min_price">Min Price:</label>
                <input type="text" name="min_price" id="min_price" value="<%=spriceLowerBound != null ? spriceLowerBound : "0.00" %>" size="5"> &emsp;
                <label for="max_price">Max Price:</label>
                <input type="text" name="max_price" id="max_price" value="<%=spriceUpperBound != null ? spriceUpperBound : "1000.00" %>" size="5"><br><br>
                <button type="submit">Filter Rooms</button><br>
            </form>
        </center><br>
        <h2>Resultant Rooms (<%=allRooms.size()%> Results)</h2>
        <% for (Room r : allRooms) {%>
            <%=r.toString()%>
        <% } %>
    </body>
</html>
