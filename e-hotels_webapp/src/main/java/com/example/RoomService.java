package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class RoomService {

    public List<Room> getRooms() throws Exception {
        String roomsOnlyQuery = "SELECT * FROM room";
        String roomAmenitiesQuery = "SELECT * FROM room_amenities";
        String roomProblemsDamagesQuery = "SELECT * FROM room_problems_damages";
        ConnectionDB db = new ConnectionDB();

        List<Room> rooms = new ArrayList<>();

        try {
            Connection con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(roomsOnlyQuery);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String chain_name = rs.getString("chain_name");
                String hotel_id = rs.getString("hotel_id");
                int room_number = rs.getInt("room_number");
                int room_floor = rs.getInt("room_floor");
                double price = rs.getDouble("price");
                int capacity = rs.getInt("capacity");
                boolean can_be_extended = rs.getBoolean("can_be_extended");
                String view_type = rs.getString("view_type");

                Room curRoom = new Room(chain_name, hotel_id, room_number, room_floor, price, capacity, can_be_extended, view_type, new ArrayList<>(), new ArrayList<>());
                rooms.add(curRoom);
            }
            stmt = con.prepareStatement(roomAmenitiesQuery);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String chain_name = rs.getString("chain_name");
                String hotel_id = rs.getString("hotel_id");
                int room_number = rs.getInt("room_number");
                int room_floor = rs.getInt("room_floor");
                String amenity = rs.getString("amenity");
                for (Room r : rooms) {
                    if (r.getChainName().equals(chain_name) &&
                            r.getHotelID().equals(hotel_id) &&
                            r.getRoomNumber() == room_number &&
                            r.getRoomFloor() == room_floor) {
                        List<String> curAmenities = r.getAmenities();
                        curAmenities.add(amenity);
                        r.setAmenities(curAmenities);
                    }
                }
            }
            stmt = con.prepareStatement(roomProblemsDamagesQuery);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String chain_name = rs.getString("chain_name");
                String hotel_id = rs.getString("hotel_id");
                int room_number = rs.getInt("room_number");
                int room_floor = rs.getInt("room_floor");
                String problem_damage = rs.getString("problem_damage");
                for (Room r : rooms) {
                    if (r.getChainName().equals(chain_name) &&
                            r.getHotelID().equals(hotel_id) &&
                            r.getRoomNumber() == room_number &&
                            r.getRoomFloor() == room_floor) {
                        List<String> curProblemsDamages = r.getProblemsDamages();
                        curProblemsDamages.add(problem_damage);
                        r.setProblemsDamages(curProblemsDamages);
                    }
                }
            }
            rs.close();
            stmt.close();
            con.close();
            db.close();
            return rooms;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String createRoom(Room room) throws Exception {
        String message = "";
        Connection con = null;
        ConnectionDB db = new ConnectionDB();
        String insertRoomQuery = "INSERT INTO room VALUES (?, ?, ?, ?, ?, ?, ?, CAST(? AS view_enum))";
        String insertRoomAmenityQuery = "INSERT INTO room_amenities VALUES (?, ?, ?, ?, ?)";
        String insertRoomProblemDamageQuery = "INSERT INTO room_problems_damages VALUES (?, ?, ?, ?, ?)";
        try {
            con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(insertRoomQuery);
            stmt.setString(1, room.getChainName());
            stmt.setString(2, room.getHotelID());
            stmt.setInt(3, room.getRoomNumber());
            stmt.setInt(4, room.getRoomFloor());
            stmt.setDouble(5, room.getPrice());
            stmt.setInt(6, room.getCapacity());
            stmt.setBoolean(7, room.canBeExtended());
            stmt.setString(8, room.getViewType());
            int output = stmt.executeUpdate();
            for (String amenity : room.getAmenities()) {
                stmt = con.prepareStatement(insertRoomAmenityQuery);
                stmt.setString(1, room.getChainName());
                stmt.setString(2, room.getHotelID());
                stmt.setInt(3, room.getRoomNumber());
                stmt.setInt(4, room.getRoomFloor());
                stmt.setString(5, amenity);
                output += stmt.executeUpdate();
            }
            for (String problem_damage : room.getProblemsDamages()) {
                stmt = con.prepareStatement(insertRoomProblemDamageQuery);
                stmt.setString(1, room.getChainName());
                stmt.setString(2, room.getHotelID());
                stmt.setInt(3, room.getRoomNumber());
                stmt.setInt(4, room.getRoomFloor());
                stmt.setString(5, problem_damage);
                output += stmt.executeUpdate();
            }
            System.out.println(output);
            stmt.close();
            db.close();
        } catch (Exception e) {
            message = "Error while inserting room: " + e.getMessage();
        } finally {
            if (con != null) {
                con.close();
            }
            if (message.equals("")) {
                message = "Room successfully inserted!";
            }
        }
        return message;
    }

    public String updateRoom(Room room) throws Exception {
        Connection con = null;
        String message = "";
        String updateRoomQuery = "UPDATE room SET " +
                "price = ?, " +
                "capacity = ?, " +
                "can_be_extended = ?, " +
                "view_type = CAST(? AS view_enum) " +
                "WHERE chain_name = ? AND hotel_id = ? AND room_number = ? AND room_floor = ?";
        String deleteExistingRoomAmenitiesQuery = "DELETE FROM room_amenities WHERE chain_name = ? AND hotel_id = ? AND room_number = ? AND room_floor = ?";
        String addNewRoomAmenitiesQuery = "INSERT INTO room_amenities VALUES (?, ?, ?, ?, ?)";
        String deleteExistingRoomProblemsDamagesQuery = "DELETE FROM room_problems_damages WHERE chain_name = ? AND hotel_id = ? AND room_number = ? AND room_floor = ?";
        String addNewRoomProblemsDamagesQuery = "INSERT INTO room_problems_damages VALUES (?, ?, ?, ?, ?)";
        ConnectionDB db = new ConnectionDB();
        try {
            con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(updateRoomQuery);
            stmt.setDouble(1, room.getPrice());
            stmt.setInt(2, room.getCapacity());
            stmt.setBoolean(3, room.canBeExtended());
            stmt.setString(4, room.getViewType());
            stmt.setString(5, room.getChainName());
            stmt.setString(6, room.getHotelID());
            stmt.setInt(7, room.getRoomNumber());
            stmt.setInt(8, room.getRoomFloor());
            int output = stmt.executeUpdate();
            stmt = con.prepareStatement(deleteExistingRoomAmenitiesQuery);
            stmt.setString(1, room.getChainName());
            stmt.setString(2, room.getHotelID());
            stmt.setInt(3, room.getRoomNumber());
            stmt.setInt(4, room.getRoomFloor());
            output += stmt.executeUpdate();
            for (String amenity : room.getAmenities()) {
                stmt = con.prepareStatement(addNewRoomAmenitiesQuery);
                stmt.setString(1, room.getChainName());
                stmt.setString(2, room.getHotelID());
                stmt.setInt(3, room.getRoomNumber());
                stmt.setInt(4, room.getRoomFloor());
                stmt.setString(5, amenity);
                output += stmt.executeUpdate();
            }
            stmt = con.prepareStatement(deleteExistingRoomProblemsDamagesQuery);
            stmt.setString(1, room.getChainName());
            stmt.setString(2, room.getHotelID());
            stmt.setInt(3, room.getRoomNumber());
            stmt.setInt(4, room.getRoomFloor());
            output += stmt.executeUpdate();
            for (String problem_damage : room.getProblemsDamages()) {
                stmt = con.prepareStatement(addNewRoomProblemsDamagesQuery);
                stmt.setString(1, room.getChainName());
                stmt.setString(2, room.getHotelID());
                stmt.setInt(3, room.getRoomNumber());
                stmt.setInt(4, room.getRoomFloor());
                stmt.setString(5, problem_damage);
                output += stmt.executeUpdate();
            }
            System.out.println(output);
            stmt.close();
            db.close();
        } catch (Exception e) {
            message = "Error while updating room: " + e.getMessage();
        } finally {
            if (con != null) {
                con.close();
            }
            if (message.equals("")) {
                message = "Room successfully updated!";
            }
        }
        return message;
    }

    public String deleteRoom(Room room) throws Exception {
        Connection con = null;
        String message = "";
        String deleteRoomQuery = "DELETE FROM room WHERE chain_name = ? AND hotel_id = ? AND room_number = ? AND room_floor = ?";
        ConnectionDB db = new ConnectionDB();
        try {
            con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(deleteRoomQuery);
            stmt.setString(1, room.getChainName());
            stmt.setString(2, room.getHotelID());
            stmt.setInt(3, room.getRoomNumber());
            stmt.setInt(4, room.getRoomFloor());
            int output = stmt.executeUpdate();
            System.out.println(output);
            stmt.close();
            db.close();
        } catch (Exception e) {
            message = "Error while deleting room: " + e.getMessage();
        } finally {
            if (con != null) {
                con.close();
            }
            if (message.equals("")) {
                message = "Room successfully deleted!";
            }
        }
        return message;
    }
}
