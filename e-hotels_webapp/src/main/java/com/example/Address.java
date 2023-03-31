package com.example;

public class Address {

    private String street_address;
    private String city;
    private String province_state;
    private String country;
    private String zip_postal_code;

    public Address (String street_address, String city, String province_state, String country, String zip_postal_code) {
        this.street_address = street_address;
        this.city = city;
        this.province_state = province_state;
        this.country = country;
        this.zip_postal_code = zip_postal_code;
    }

    public String getStreetAddress() { return street_address; }
    public String getCity() { return city; }
    public String getProvinceState() { return province_state; }
    public String getCountry() { return country; }
    public String getZipPostalCode() { return zip_postal_code; }

    @Override
    public String toString() {
        return street_address + ", " + city + ", " + province_state + ", " + country + ", " + zip_postal_code;
    }
}
