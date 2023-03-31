package com.example;

import java.util.ArrayList;
import java.util.List;

public class PruneUtil {

    public static List<Room> pruneRooms(List<Room> allRooms,
                                        String startDate, String endDate,
                                        int capacityLowerBound, int capacityUpperBound,
                                        String city, String chain,
                                        int numStarsLowerBound, int numStarsUpperBound,
                                        double priceLowerBound, double priceUpperBound) throws Exception {
        List<Room> prunedList = new ArrayList<>();
        // Plan: run the rooms through each constraint, each time using the pruned list.

        if (startDate == null) startDate = "0";
        if (endDate == null) endDate = "5";
        if (city == null) city = "";
        if (chain == null) chain = "";

        BookingService bookingService = new BookingService();
        List<Booking> allBookings = bookingService.getBookings();
        for (Room r : allRooms) {
            boolean flag = true;
            for (Booking b : allBookings) {
                if (r.getChainName().equals(b.getChainName()) &&
                        r.getHotelID().equals(b.getHotelID()) &&
                        r.getRoomNumber() == b.getRoomNumber() &&
                        r.getRoomFloor() == b.getRoomFloor()) {
                    if ((b.getStartDate().compareTo(startDate) > 0 && b.getStartDate().compareTo(endDate) < 0) ||
                        (b.getEndDate().compareTo(startDate) > 0 && b.getEndDate().compareTo(endDate) < 0) ||
                        (b.getStartDate().compareTo(startDate) <= 0 && b.getEndDate().compareTo(endDate) >= 0)) {
                        flag = false;
                        break;
                    }
                }
            }
            if (flag) {
                prunedList.add(r);
            }
        }
        List<Room> prunedList2 = new ArrayList<>();
        for (Room r : prunedList) {
            if (r.getCapacity() >= capacityLowerBound && r.getCapacity() <= capacityUpperBound) {
                prunedList2.add(r);
            }
        }

        List<Room> prunedList3 = new ArrayList<>();
        HotelService hotelService = new HotelService();
        List<Hotel> allHotels = hotelService.getHotels();
        for (Room r : prunedList2) {
            for (Hotel h : allHotels) {
                if (r.getChainName().equals(h.getChainName()) && r.getHotelID().equals(h.getHotelID())) {
                    if (h.getAddress().getCity().equalsIgnoreCase(city) || city.length() == 0) {
                        prunedList3.add(r);
                        break;
                    }
                }
            }
        }

        List<Room> prunedList4 = new ArrayList<>();
        for (Room r : prunedList3) {
            if (r.getChainName().equalsIgnoreCase(chain) || chain.length() == 0) {
                prunedList4.add(r);
            }
        }

        List<Room> prunedList5 = new ArrayList<>();
        for (Room r : prunedList4) {
            for (Hotel h : allHotels) {
                if (r.getChainName().equals(h.getChainName()) && r.getHotelID().equals(h.getHotelID())) {
                    if (h.getNumStars() >= numStarsLowerBound && h.getNumStars() <= numStarsUpperBound) {
                        prunedList5.add(r);
                        break;
                    }
                }
            }
        }

        List<Room> prunedList6 = new ArrayList<>();
        for (Room r : prunedList5) {
            if (r.getPrice() >= priceLowerBound && r.getPrice() <= priceUpperBound) {
                prunedList6.add(r);
            }
        }
        return prunedList6;
    }
}
