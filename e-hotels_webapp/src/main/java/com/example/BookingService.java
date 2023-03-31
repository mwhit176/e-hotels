package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookingService {

    public List<Booking> getBookings() throws Exception {
        String bookingQuery = "SELECT * FROM booking";
        ConnectionDB db = new ConnectionDB();

        List<Booking> bookings = new ArrayList<>();

        try {
            Connection con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(bookingQuery);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String chain_name = rs.getString("chain_name");
                String hotel_id = rs.getString("hotel_id");
                int room_number = rs.getInt("room_number");
                int room_floor = rs.getInt("room_floor");
                String customer_sin_ssn = rs.getString("customer_sin_ssn");
                String start_date = rs.getString("start_date");
                String end_date = rs.getString("end_date");

                Booking curBooking = new Booking(chain_name, hotel_id, room_number, room_floor, customer_sin_ssn, start_date, end_date);
                bookings.add(curBooking);
            }
            rs.close();
            stmt.close();
            con.close();
            db.close();
            return bookings;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String createBooking(Booking booking) throws Exception {
        String message = "";
        Connection con = null;
        ConnectionDB db = new ConnectionDB();
        String insertBookingQuery = "INSERT INTO booking VALUES (?, ?, ?, ?, ?, CAST(? AS DATE), CAST(? AS DATE))";
        try {
            con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(insertBookingQuery);
            stmt.setString(1, booking.getChainName());
            stmt.setString(2, booking.getHotelID());
            stmt.setInt(3, booking.getRoomNumber());
            stmt.setInt(4, booking.getRoomFloor());
            stmt.setString(5, booking.getCustomerSinSsn());
            stmt.setString(6, booking.getStartDate());
            stmt.setString(7, booking.getEndDate());
            int output = stmt.executeUpdate();
            System.out.println(output);
            stmt.close();
            db.close();
        } catch (Exception e) {
            message = "Error while inserting booking: " + e.getMessage();
        } finally {
            if (con != null) {
                con.close();
            }
            if (message.equals("")) {
                message = "Booking successfully inserted!";
            }
        }
        return message;
    }
}
