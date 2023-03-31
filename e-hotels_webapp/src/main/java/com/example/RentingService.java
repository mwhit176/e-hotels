package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RentingService {

    public List<Renting> getRentings() throws Exception {
        String rentingQuery = "SELECT * FROM renting";
        ConnectionDB db = new ConnectionDB();

        List<Renting> rentings = new ArrayList<>();

        try {
            Connection con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(rentingQuery);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String chain_name = rs.getString("chain_name");
                String hotel_id = rs.getString("hotel_id");
                int room_number = rs.getInt("room_number");
                int room_floor = rs.getInt("room_floor");
                String customer_sin_ssn = rs.getString("customer_sin_ssn");
                String start_date = rs.getString("start_date");
                String end_date = rs.getString("end_date");

                Renting curRenting = new Renting(chain_name, hotel_id, room_number, room_floor, customer_sin_ssn, start_date, end_date);
                rentings.add(curRenting);
            }
            rs.close();
            stmt.close();
            con.close();
            db.close();
            return rentings;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String createRenting(Renting renting) throws Exception {
        String message = "";
        Connection con = null;
        ConnectionDB db = new ConnectionDB();
        String insertRentingQuery = "INSERT INTO renting VALUES (?, ?, ?, ?, ?, CAST(? AS DATE), CAST(? AS DATE))";
        try {
            con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(insertRentingQuery);
            stmt.setString(1, renting.getChainName());
            stmt.setString(2, renting.getHotelID());
            stmt.setInt(3, renting.getRoomNumber());
            stmt.setInt(4, renting.getRoomFloor());
            stmt.setString(5, renting.getCustomerSinSsn());
            stmt.setString(6, renting.getStartDate());
            stmt.setString(7, renting.getEndDate());
            int output = stmt.executeUpdate();
            System.out.println(output);
            stmt.close();
            db.close();
        } catch (Exception e) {
            message = "Error while inserting renting: " + e.getMessage();
        } finally {
            if (con != null) {
                con.close();
            }
            if (message.equals("")) {
                message = "Renting successfully inserted!";
            }
        }
        return message;
    }
}
