package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.*;

public class HotelManagerServlet extends HttpServlet {

        public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try {
                String chain = request.getParameter("chain");
                String hotel_id = request.getParameter("hotel_id");
                String street_address = request.getParameter("street_address");
                String city = request.getParameter("city");
                String province_state = request.getParameter("province_state");
                String country = request.getParameter("country");
                String zip_postal_code = request.getParameter("zip_postal_code");
                int rating = Integer.parseInt(request.getParameter("rating"));
                String email = request.getParameter("email");
                String phones = request.getParameter("phones");
                String mgr_sin_ssn = request.getParameter("mgr_sin_ssn");

                List<String> phoneNumbers = Arrays.asList(phones.split("[,; ]"));

                HotelService hotelService = new HotelService();
                Hotel newHotel = new Hotel(chain, hotel_id,
                        new Address(street_address, city, province_state, country, zip_postal_code), 0,
                        rating, email, phoneNumbers, mgr_sin_ssn);

                List<Hotel> allHotels = hotelService.getHotels();
                List<String> allHotelChains = new ArrayList<>();
                List<String> allHotelsIds = new ArrayList<>();
                for (Hotel h : allHotels) {
                    allHotelChains.add(h.getChainName());
                    allHotelsIds.add(h.getHotelID());
                }

                int exists = -1;
                for (int i = 0; i < allHotelChains.size(); i++) {
                    if (chain.equals(allHotelChains.get(i)) && hotel_id.equals(allHotelsIds.get(i))) {
                        exists = i;
                        break;
                    }
                }
                String test;
                if (exists >= 0) {
                    newHotel.setNumRooms(allHotels.get(exists).getNumRooms());
                    test = hotelService.updateHotel(newHotel);
                } else {
                    test = hotelService.createHotel(newHotel);
                }

                System.out.println(test);
                if (test.startsWith("Error")) {
                    throw new Exception(test);
                }
                response.sendRedirect("index.jsp");
            } catch (Exception e) {
                System.out.println(e);
                response.sendRedirect("manage-hotels.jsp");
            }
        }

        public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try {
                String chain = request.getParameter("del_chain");
                String hotel_id = request.getParameter("del_hotel_id");

                HotelService hotelService = new HotelService();
                Hotel newHotel = new Hotel(chain, hotel_id, null, 0, 1, null, null, null);
                String test = hotelService.deleteHotel(newHotel);
                System.out.println(test);
                if (test.startsWith("Error")) {
                    throw new Exception(test);
                }
                response.sendRedirect("index.jsp");
            } catch (Exception e) {
                System.out.println(e);
                response.sendRedirect("manage-hotels.jsp");
            }
        }
}
