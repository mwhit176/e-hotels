package com.example;

import java.util.List;

public class Hotel {

    private String chain_name;
    private String hotel_id;
    private Address address;
    private int num_rooms;
    private int num_stars;
    private String email_address;
    private List<String> phone_numbers;
    private String manager_ssn;

    public Hotel(String chain_name, String hotel_id, Address address, int num_rooms, int num_stars, String email_address, List<String> phone_numbers, String manager_ssn) {
        this.chain_name = chain_name;
        this.hotel_id = hotel_id;
        this.address = address;
        this.num_rooms = num_rooms;
        this.num_stars = num_stars;
        this.email_address = email_address;
        this.phone_numbers = phone_numbers;
        this.manager_ssn = manager_ssn;
    }

    public String getChainName() { return chain_name; }
    public String getHotelID() { return hotel_id; }
    public Address getAddress() { return address; }
    public int getNumRooms() { return num_rooms; }
    public int getNumStars() { return num_stars; }
    public String getEmailAddress() { return email_address; }
    public List<String> getPhoneNumbers() { return phone_numbers; };
    public String getManagerSSN() { return manager_ssn; }

    public void setPhoneNumbers(List<String> phone_numbers) { this.phone_numbers = phone_numbers; }
    public void setNumRooms(int num_rooms) { this.num_rooms = num_rooms; }

    @Override
    public String toString() {
        return chain_name + ", Hotel " + hotel_id + "<ul>" +
                "<li>" + address + "</li>" +
                "<li>" + num_rooms + " rooms, " + num_stars + " stars</li>" +
                "<li>Email: " + email_address + "</li>" +
                "<li>Phone Numbers: " + phone_numbers.toString() + " </li>" +
                "<li>Manager SIN/SSN: " + manager_ssn + "</ul>";
    }

}
