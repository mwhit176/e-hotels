package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BookingArchiveService {

    public List<Booking> getBookings() throws Exception {
        String bookingQuery = "SELECT * FROM booking_archive";
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
}
