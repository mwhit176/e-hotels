package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpecialViewService {

    public List<List<String>> getNumRoomsPerArea() throws Exception {
        String sql = "SELECT * FROM rooms_per_area";
        ConnectionDB db = new ConnectionDB();

        List<List<String>> result = new ArrayList<>();

        try {
            Connection con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String city = rs.getString("city");
                String province_state = rs.getString("province_state");
                String country = rs.getString("country");
                String num_rooms = Integer.toString(rs.getInt("num_rooms"));

                result.add(Arrays.asList(Integer.toString(result.size() + 1), city, province_state, country, num_rooms));
            }
            rs.close();
            stmt.close();
            con.close();
            db.close();
            return result;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public List<List<String>> getHotelRoomCapacity() throws Exception {
        String sql = "SELECT * FROM hotel_room_capacity";
        ConnectionDB db = new ConnectionDB();

        List<List<String>> result = new ArrayList<>();

        try {
            Connection con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String room_number = Integer.toString(rs.getInt("room_number"));
                String room_floor = Integer.toString(rs.getInt("room_floor"));
                String capacity = Integer.toString(rs.getInt("capacity"));

                result.add(Arrays.asList(Integer.toString(result.size() + 1), room_number, room_floor, capacity));
            }
            rs.close();
            stmt.close();
            con.close();
            db.close();
            return result;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

}
