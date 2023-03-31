package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RoomManagerServlet extends HttpServlet {

        public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try {
                String chain = request.getParameter("chain");
                String hotel_id = request.getParameter("hotel_id");
                int number = Integer.parseInt(request.getParameter("number"));
                int floor = Integer.parseInt(request.getParameter("floor"));
                double price = Double.parseDouble(request.getParameter("price"));
                int capacity = Integer.parseInt(request.getParameter("capacity"));
                boolean can_extend = Boolean.parseBoolean(request.getParameter("extends"));
                String view = request.getParameter("view");
                String amenities = request.getParameter("amenities");
                String problems = request.getParameter("problems");

                List<String> room_amenities = Arrays.asList(amenities.split("[,; ]"));
                List<String> problems_damages = Arrays.asList(problems.split("[,; ]"));

                RoomService roomService = new RoomService();
                Room newRoom = new Room(chain, hotel_id, number, floor,
                        price, capacity, can_extend, view, room_amenities, problems_damages);

                List<Room> allRooms = roomService.getRooms();
                List<String> allRoomChains = new ArrayList<>();
                List<String> allRoomHotelIds = new ArrayList<>();
                List<Integer> allRoomNumbers = new ArrayList<>();
                List<Integer> allRoomFloors = new ArrayList<>();
                for (Room r : allRooms) {
                    allRoomChains.add(r.getChainName());
                    allRoomHotelIds.add(r.getHotelID());
                    allRoomNumbers.add(r.getRoomNumber());
                    allRoomFloors.add(r.getRoomFloor());
                }

                int exists = -1;
                for (int i = 0; i < allRoomChains.size(); i++) {
                    if (chain.equals(allRoomChains.get(i)) && hotel_id.equals(allRoomHotelIds.get(i)) &&
                                        number == allRoomNumbers.get(i) && floor == allRoomFloors.get(i)) {
                        exists = i;
                        break;
                    }
                }
                String test;
                if (exists >= 0) {
                    test = roomService.updateRoom(newRoom);
                } else {
                    test = roomService.createRoom(newRoom);
                }

                System.out.println(test);
                if (test.startsWith("Error")) {
                    throw new Exception(test);
                }
                response.sendRedirect("index.jsp");
            } catch (Exception e) {
                System.out.println(e);
                response.sendRedirect("manage-rooms.jsp");
            }
        }

        public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try {
                String chain = request.getParameter("del_chain");
                String hotel_id = request.getParameter("del_hotel_id");
                int number = Integer.parseInt(request.getParameter("del_number"));
                int floor = Integer.parseInt(request.getParameter("del_floor"));

                RoomService roomService = new RoomService();
                Room newRoom = new Room(chain, hotel_id, number, floor, 0, 0, false, null, null, null);
                String test = roomService.deleteRoom(newRoom);
                System.out.println(test);
                if (test.startsWith("Error")) {
                    throw new Exception(test);
                }
                response.sendRedirect("index.jsp");
            } catch (Exception e) {
                System.out.println(e);
                response.sendRedirect("manage-rooms.jsp");
            }
        }
}
