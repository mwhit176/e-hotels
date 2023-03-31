package com.example;

import java.util.List;

public class Chain {

    private String chain_name;
    private int num_hotels;
    private List<String> email_addresses;
    private List<String> phone_numbers;
    private List<Address> addresses;

    public Chain(String chain_name, int num_hotels, List<String> email_addresses, List<String> phone_numbers, List<Address> addresses) {
        this.chain_name = chain_name;
        this.num_hotels = num_hotels;
        this.email_addresses = email_addresses;
        this.phone_numbers = phone_numbers;
        this.addresses = addresses;
    }


    public String getChainName() { return chain_name; }
    public int getNumHotels() { return num_hotels; }
    public List<String> getEmailAddresses() { return email_addresses; }
    public List<String> getPhoneNumbers() { return phone_numbers; }
    public List<Address> getAddresses() { return addresses; }

    public void setChainName(String chain_name) { this.chain_name = chain_name; }
    public void setNumHotels(int num_hotels) { this.num_hotels = num_hotels; }
    public void setEmailAddresses(List<String> email_addresses) { this.email_addresses = email_addresses; }
    public void setPhoneNumbers (List<String> phone_numbers) { this.phone_numbers = phone_numbers; }
    public void setAddresses (List<Address> addresses) { this.addresses = addresses; }

    @Override
    public String toString() {
        return "CHAIN\n\tChain Name: " + chain_name + "\n" +
                "\tNumber of Hotels: " + num_hotels + "\n" +
                "\tEmail Addresses: " + email_addresses.toString() + "\n" +
                "\tPhone Numbers: " + phone_numbers.toString() + "\n" +
                "\tOffice Addresses: " + addresses.toString();
    }
}
