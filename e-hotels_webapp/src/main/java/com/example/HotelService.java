package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HotelService {

    public List<Hotel> getHotels() throws Exception {
        String hotelOnlyQuery = "SELECT * FROM hotel";
        String hotelPhoneNumbersQuery = "SELECT * FROM hotel_phone_numbers";
        ConnectionDB db = new ConnectionDB();

        List<Hotel> hotels = new ArrayList<>();

        try {
            Connection con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(hotelOnlyQuery);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String chain_name = rs.getString("chain_name");
                String hotel_id = rs.getString("hotel_id");
                String street_address = rs.getString("street_address");
                String city = rs.getString("city");
                String province_state = rs.getString("province_state");
                String country = rs.getString("country");
                String postal_zip_code = rs.getString("zip_postal_code");
                int num_rooms = rs.getInt("num_rooms");
                int num_stars = rs.getInt("num_stars");
                String email_address = rs.getString("email_address");
                String mgr_ssn = rs.getString("emp_sin_ssn");
                Address address = new Address(street_address, city, province_state, country, postal_zip_code);

                Hotel curHotel = new Hotel(chain_name, hotel_id, address, num_rooms, num_stars, email_address, new ArrayList<>(), mgr_ssn);
                hotels.add(curHotel);
            }
            stmt = con.prepareStatement(hotelPhoneNumbersQuery);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String chain_name = rs.getString("chain_name");
                String hotel_id = rs.getString("hotel_id");
                String phone_number = rs.getString("phone_number");
                for (Hotel h : hotels) {
                    if (h.getChainName().equals(chain_name) &&
                        h.getHotelID().equals(hotel_id)) {
                        List<String> curPhoneNumbers = h.getPhoneNumbers();
                        curPhoneNumbers.add(phone_number);
                        h.setPhoneNumbers(curPhoneNumbers);
                    }
                }
            }
            rs.close();
            stmt.close();
            con.close();
            db.close();
            return hotels;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String createHotel(Hotel hotel) throws Exception {
        String message = "";
        Connection con = null;
        ConnectionDB db = new ConnectionDB();
        String insertHotelQuery = "INSERT INTO hotel VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String insertHotelPhoneNumbersQuery = "INSERT INTO hotel_phone_numbers VALUES (?, ?, ?)";
        try {
            con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(insertHotelQuery);
            stmt.setString(1, hotel.getChainName());
            stmt.setString(2, hotel.getHotelID());
            stmt.setString(3, hotel.getAddress().getStreetAddress());
            stmt.setString(4, hotel.getAddress().getCity());
            stmt.setString(5, hotel.getAddress().getProvinceState());
            stmt.setString(6, hotel.getAddress().getCountry());
            stmt.setString(7, hotel.getAddress().getZipPostalCode());
            stmt.setInt(8, hotel.getNumRooms());
            stmt.setInt(9, hotel.getNumStars());
            stmt.setString(10, hotel.getEmailAddress());
            stmt.setString(11, hotel.getManagerSSN());
            int output = stmt.executeUpdate();
            for (String phone_number : hotel.getPhoneNumbers()) {
                stmt = con.prepareStatement(insertHotelPhoneNumbersQuery);
                stmt.setString(1, hotel.getChainName());
                stmt.setString(2, hotel.getHotelID());
                stmt.setString(3, phone_number);
                output += stmt.executeUpdate();
            }
            System.out.println(output);
            stmt.close();
            db.close();
        } catch (Exception e) {
            message = "Error while inserting hotel: " + e.getMessage();
        } finally {
            if (con != null) {
                con.close();
            }
            if (message.equals("")) {
                message = "Hotel successfully inserted!";
            }
        }
        return message;
    }

    public String updateHotel(Hotel hotel) throws Exception {
        Connection con = null;
        String message = "";
        String updateHotelQuery = "UPDATE hotel SET " +
                "street_address = ?, " +
                "city = ?, " +
                "province_state = ?, " +
                "country = ?, " +
                "zip_postal_code = ?, " +
                "num_rooms = ?, " +
                "num_stars = ?, " +
                "email_address = ?, " +
                "emp_sin_ssn = ? " +
                "WHERE chain_name = ? AND hotel_id = ?";
        String deleteExistingHotelPhoneNumbersQuery = "DELETE FROM hotel_phone_numbers WHERE chain_name = ? AND hotel_id = ?";
        String addNewHotelPhoneNumbersQuery = "INSERT INTO hotel_phone_numbers VALUES (?, ?, ?)";
        ConnectionDB db = new ConnectionDB();
        try {
            con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(updateHotelQuery);
            stmt.setString(1, hotel.getAddress().getStreetAddress());
            stmt.setString(2, hotel.getAddress().getCity());
            stmt.setString(3, hotel.getAddress().getProvinceState());
            stmt.setString(4, hotel.getAddress().getCountry());
            stmt.setString(5, hotel.getAddress().getZipPostalCode());
            stmt.setInt(6, hotel.getNumRooms());
            stmt.setInt(7, hotel.getNumStars());
            stmt.setString(8, hotel.getEmailAddress());
            stmt.setString(9, hotel.getManagerSSN());
            stmt.setString(10, hotel.getChainName());
            stmt.setString(11, hotel.getHotelID());
            int output = stmt.executeUpdate();
            stmt = con.prepareStatement(deleteExistingHotelPhoneNumbersQuery);
            stmt.setString(1, hotel.getChainName());
            stmt.setString(2, hotel.getHotelID());
            output += stmt.executeUpdate();
            for (String phone_number : hotel.getPhoneNumbers()) {
                stmt = con.prepareStatement(addNewHotelPhoneNumbersQuery);
                stmt.setString(1, hotel.getChainName());
                stmt.setString(2, hotel.getHotelID());
                stmt.setString(3, phone_number);
                output += stmt.executeUpdate();
            }
            System.out.println(output);
            stmt.close();
            db.close();
        } catch (Exception e) {
            message = "Error while updating hotel: " + e.getMessage();
        } finally {
            if (con != null) {
                con.close();
            }
            if (message.equals("")) {
                message = "Hotel successfully updated!";
            }
        }
        return message;
    }

    public String deleteHotel(Hotel hotel) throws Exception {
        Connection con = null;
        String message = "";
        String deleteHotelQuery = "DELETE FROM hotel WHERE chain_name = ? AND hotel_id = ?";
        ConnectionDB db = new ConnectionDB();
        try {
            con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(deleteHotelQuery);
            stmt.setString(1, hotel.getChainName());
            stmt.setString(2, hotel.getHotelID());
            int output = stmt.executeUpdate();
            System.out.println(output);
            stmt.close();
            db.close();
        } catch (Exception e) {
            message = "Error while deleting hotel: " + e.getMessage();
        } finally {
            if (con != null) {
                con.close();
            }
            if (message.equals("")) {
                message = "Hotel successfully deleted!";
            }
        }
        return message;
    }
}
