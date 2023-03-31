package com.example;

import java.io.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class CustomerServlet extends HttpServlet {

        public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            System.out.println("Received!!");
        }

        public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try {
                String chain_name = request.getParameter("book_chain_name");
                String hotel_id = request.getParameter("book_hotel_id");
                int room_number = Integer.parseInt(request.getParameter("book_room_number"));
                int room_floor = Integer.parseInt(request.getParameter("book_room_floor"));
                String customer_sin_ssn = request.getParameter("book_sin");
                String start_date = request.getParameter("book_start_date");
                String end_date = request.getParameter("book_end_date");
                BookingService bookingService = new BookingService();
                Booking newBooking = new Booking(chain_name, hotel_id, room_number, room_floor, customer_sin_ssn, start_date, end_date);
                String test = bookingService.createBooking(newBooking);
                System.out.println(test);
                if (test.startsWith("Error")) {
                    throw new Exception(test);
                }
                response.sendRedirect("index.jsp");
            } catch (Exception e) {
                System.out.println(e);
                response.sendRedirect("customer-view.jsp");
            }
        }
}
