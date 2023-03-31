package com.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ChainService {

    public List<Chain> getChains() throws Exception {
        String chainsOnlyQuery = "SELECT * FROM chain";
        String chainEmailAddressesQuery = "SELECT * FROM chain_email_addresses";
        String chainAddressesQuery = "SELECT * FROM chain_office_addresses";
        String chainPhoneNumbersQuery = "SELECT * FROM chain_phone_numbers";
        ConnectionDB db = new ConnectionDB();

        List<Chain> chains = new ArrayList<>();

        try {
            Connection con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(chainsOnlyQuery);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String chain_name = rs.getString("chain_name");
                int num_hotels = rs.getInt("num_hotels");
                Chain curChain = new Chain(chain_name, num_hotels, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
                chains.add(curChain);
            }
            stmt = con.prepareStatement(chainEmailAddressesQuery);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String chain_name = rs.getString("chain_name");
                String email_address = rs.getString("email_address");
                for (Chain c : chains) {
                    if (c.getChainName().equals(chain_name)) {
                        List<String> curEmailAddresses = c.getEmailAddresses();
                        curEmailAddresses.add(email_address);
                        c.setEmailAddresses(curEmailAddresses);
                    }
                }
            }
            stmt = con.prepareStatement(chainPhoneNumbersQuery);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String chain_name = rs.getString("chain_name");
                String phone_number = rs.getString("phone_number");
                for (Chain c : chains) {
                    if (c.getChainName().equals(chain_name)) {
                        List<String> curPhoneNumbers = c.getPhoneNumbers();
                        curPhoneNumbers.add(phone_number);
                        c.setPhoneNumbers(curPhoneNumbers);
                    }
                }
            }
            stmt = con.prepareStatement(chainAddressesQuery);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String chain_name = rs.getString("chain_name");
                String street_address = rs.getString("street_address");
                String city = rs.getString("city");
                String province_state = rs.getString("province_state");
                String country = rs.getString("country");
                String zip_postal_code = rs.getString("zip_postal_code");
                Address address = new Address(street_address, city, province_state, country, zip_postal_code);

                for (Chain c : chains) {
                    if (c.getChainName().equals(chain_name)) {
                        List<Address> curAddresses = c.getAddresses();
                        curAddresses.add(address);
                        c.setAddresses(curAddresses);
                    }
                }
            }
            rs.close();
            stmt.close();
            con.close();
            db.close();
            return chains;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    public String deleteChain(Chain chain) throws Exception {
        Connection con = null;
        String message = "";
        String deleteChainQuery = "DELETE FROM chain WHERE chain_name = ?";
        ConnectionDB db = new ConnectionDB();
        try {
            con = db.getConnection();
            PreparedStatement stmt = con.prepareStatement(deleteChainQuery);
            stmt.setString(1, chain.getChainName());
            int output = stmt.executeUpdate();
            System.out.println(output);
            stmt.close();
            db.close();
        } catch (Exception e) {
            message = "Error while deleting chain: " + e.getMessage();
        } finally {
            if (con != null) {
                con.close();
            }
            if (message.equals("")) {
                message = "Chain successfully deleted!";
            }
        }
        return message;
    }
}
