package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class RentingArchiveService {

    public List<Renting> getRentings() throws Exception {
        String rentingQuery = "SELECT * FROM renting_archive";
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
}
