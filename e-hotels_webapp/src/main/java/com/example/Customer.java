package com.example;

import java.util.Map;

public class Customer {
    private String first_name;
    private String middle_name;
    private String last_name;
    private String customer_sin_ssn;
    private Address address;
    private Map<String, String> registrations;

    public Customer(String first_name, String middle_name, String last_name, String customer_sin_ssn, Address address, Map<String, String> registrations) {
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.customer_sin_ssn = customer_sin_ssn;
        this.address = address;
        this.registrations = registrations;
    }

    public String getFirstName() { return first_name; }
    public String getMiddleName() { return middle_name; }
    public String getLastName() { return last_name; }
    public String getCustomerSinSsn() { return customer_sin_ssn; }
    public Address getAddress() { return address; }
    public Map<String, String> getRegistrations() { return registrations; };

    public void setFirstName(String first_name) { this.first_name = first_name; }
    public void setMiddleName(String middle_name) { this.middle_name = middle_name; }
    public void setLastName(String last_name) { this.last_name = last_name; }
    public void setCustomerSinSsn(String customer_sin_ssn) { this.customer_sin_ssn = customer_sin_ssn; }
    public void setAddress(Address address) { this.address = address; }
    public void setRegistrations(Map<String, String> registrations) { this.registrations = registrations; };

    @Override
    public String toString() {
        String registrationsString = "[";
        for (String chain : registrations.keySet()) {
            registrationsString += "[" + chain + ", " + registrations.get(chain) + "], ";
        }
        if (registrationsString.length() > 1) {
            registrationsString = registrationsString.substring(0, registrationsString.length() - 2);
        }
        registrationsString += "]";

        return first_name + (middle_name == null ? " " : " " + middle_name + " ") + last_name + ": " + customer_sin_ssn + "<ul>" +
                "<li>Address: " + address + "</li>" +
                "<li>Registrations: " + registrationsString + "</li></ul>";
    }
}
