package com.example;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class EmployeeServlet extends HttpServlet {

        public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            System.out.println("Received!!");
        }

        public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            try {
                String chain_name = request.getParameter("rent_chain_name");
                String hotel_id = request.getParameter("rent_hotel_id");
                int room_number = Integer.parseInt(request.getParameter("rent_room_number"));
                int room_floor = Integer.parseInt(request.getParameter("rent_room_floor"));
                String customer_sin_ssn = request.getParameter("rent_sin");
                String start_date = request.getParameter("rent_start_date");
                String end_date = request.getParameter("rent_end_date");
                RentingService rentingService = new RentingService();
                Renting newRenting = new Renting(chain_name, hotel_id, room_number, room_floor, customer_sin_ssn, start_date, end_date);
                String test = rentingService.createRenting(newRenting);
                System.out.println(test);
                if (test.startsWith("Error")) {
                    throw new Exception(test);
                }
                response.sendRedirect("index.jsp");
            } catch (Exception e) {
                System.out.println(e);
                response.sendRedirect("employee-view.jsp");
            }
        }
}
