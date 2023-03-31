package com.example;

import java.util.List;

public class Room {

    private String chain_name;
    private String hotel_id;
    private int room_number;
    private int room_floor;
    private double price;
    private int capacity;
    private boolean can_be_extended;
    private String view_type;
    private List<String> amenities;
    private List<String> problems_damages;

    public Room(String chain_name, String hotel_id, int room_number, int room_floor, double price, int capacity, boolean can_be_extended, String view_type, List<String> amenities, List<String> problems_damages) {
        this.chain_name = chain_name;
        this.hotel_id = hotel_id;
        this.room_number = room_number;
        this.room_floor = room_floor;
        this.price = price;
        this.capacity = capacity;
        this.can_be_extended = can_be_extended;
        this.view_type = view_type;
        this.amenities = amenities;
        this.problems_damages = problems_damages;
    }

    public String getChainName() { return chain_name; }
    public String getHotelID() { return hotel_id; }
    public int getRoomNumber() { return room_number; }
    public int getRoomFloor() { return room_floor; }
    public double getPrice() { return price; }
    public int getCapacity() { return capacity; }
    public boolean canBeExtended() { return can_be_extended; }
    public String getViewType() { return view_type; }
    public List<String> getAmenities() { return amenities; }
    public List<String> getProblemsDamages() { return problems_damages; }

    public void setAmenities(List<String> amenities) { this.amenities = amenities; }
    public void setProblemsDamages(List<String> problems_damages) { this.problems_damages = problems_damages; }

    public String toString() {
        return chain_name + " Hotel " + hotel_id + ", room " + room_number + ", floor " + room_floor + ".<br>" +
                "<ul><li>Price: " + price + "</li>" +
                "<li>Capacity: " + capacity + "</li>" +
                "<li>Can be Extended: " + can_be_extended + "</li>" +
                "<li>View Type: " + view_type + "</li>" +
                "<li>Amenities: " + amenities.toString() + "</li>" +
                "<li>Problems/Damage: " + problems_damages.toString() + "</li></ul>";
    }
}
