package com.example;

public class Employee {
    private String first_name;
    private String middle_name;
    private String last_name;
    private String emp_sin_ssn;
    private Address address;
    private String role_position;
    private String chain_name;
    private String hotel_id;

    public Employee(String first_name, String middle_name, String last_name, String emp_sin_ssn, Address address, String role_position, String chain_name, String hotel_id) {
        this.first_name = first_name;
        this.middle_name = middle_name;
        this.last_name = last_name;
        this.emp_sin_ssn = emp_sin_ssn;
        this.address = address;
        this.role_position = role_position;
        this.chain_name = chain_name;
        this.hotel_id = hotel_id;
    }

    public String getFirstName() { return first_name; }
    public String getMiddleName() { return middle_name; }
    public String getLastName() { return last_name; }
    public String getEmpSinSsn() { return emp_sin_ssn; }
    public Address getAddress() { return address; }
    public String getRolePosition() { return role_position; }
    public String getChainName() { return chain_name; }
    public String getHotelID() { return hotel_id; }

    public void setFirstName(String first_name) { this.first_name = first_name; }
    public void setMiddleName(String middle_name) { this.middle_name = middle_name; }
    public void setLastName(String last_name) { this.last_name = last_name; }
    public void setEmpSinSsn(String emp_sin_ssn) { this.emp_sin_ssn = emp_sin_ssn; }
    public void setAddress(Address address) { this.address = address; }
    public void setRolePosition(String role_position) { this.role_position = role_position; }
    public void setChainName(String chain_name) { this.chain_name = chain_name; }
    public void setHotelID(String hotel_id) { this.hotel_id = hotel_id; }

    @Override
    public String toString() {
        return first_name + (middle_name == null ? " " : " " + middle_name + " ") + last_name + ": " + emp_sin_ssn + "<ul>" +
                "<li>Address: " + address + "</li>" +
                "<li>" + role_position + " at " + (chain_name != null ? chain_name : "?") + ", Hotel " + (hotel_id != null ? hotel_id : "?") + "</li></ul>";
    }
}
