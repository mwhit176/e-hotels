package com.example;

public class Booking {

    private String chain_name;
    private String hotel_id;
    private int room_number;
    private int room_floor;
    private String customer_sin_ssn;
    private String start_date;
    private String end_date;

    public Booking(String chain_name, String hotel_id, int room_number, int room_floor, String customer_sin_ssn, String start_date, String end_date) {
        this.chain_name = chain_name;
        this.hotel_id = hotel_id;
        this.room_number = room_number;
        this.room_floor = room_floor;
        this.customer_sin_ssn = customer_sin_ssn;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getChainName() { return chain_name; }
    public String getHotelID() { return hotel_id; }
    public int getRoomNumber() { return room_number; }
    public int getRoomFloor() { return room_floor; }
    public String getCustomerSinSsn() { return customer_sin_ssn; }
    public String getStartDate() { return start_date; }
    public String getEndDate() { return end_date; }

    public void setChainName() { this.chain_name = chain_name; }
    public void setHotelID() { this.hotel_id = hotel_id; }
    public void setRoomNumber() { this.room_number = room_number; }
    public void setRoomFloor() { this.room_floor = room_floor; }
    public void setCustomerSinSsn() { this.customer_sin_ssn = customer_sin_ssn; }
    public void setStartDate() { this.start_date = start_date; }
    public void setEndDate() { this.end_date = end_date; }

    public String toString() {
        return chain_name + " Hotel " + hotel_id + ", room " + room_number + ", floor " + room_floor + ".<br>" +
                "<ul><li>Customer SIN/SSN: " + customer_sin_ssn + "</li>" +
                "<li>From: " + start_date + " to " + end_date + "</li></ul>";
    }

}
