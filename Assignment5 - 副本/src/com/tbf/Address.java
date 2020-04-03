package com.tbf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class Address {
	private String street;
	private String city;
	private String state;
	private String zip;
	private String country;
	
	public Address(String street, String city, String state, String zip, String country) {
		super();
		this.street = street;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.country = country;
	}
	public String getStreet() {
		return street;
	}
	public String getCity() {
		return city;
	}
	public String getState() {
		return state;
	}
	public String getZip() {
		return zip;
	}
	public String getCountry() {
		return country;
	}

	public String toString() {
		return street + "\n" + city + ", " + state + ", " + zip + ", "+ country;
	}
	public static List<Address> getAddressSummaries() {
		
		List<Address> address = new ArrayList<>();
		
        //1.load the JDBC driver
        String DRIVER_CLASS = "com.mysql.jdbc.Driver";
        try {
               Class.forName(DRIVER_CLASS);
           	}catch(Exception e) {
           		throw new RuntimeException(e);
           	}
        
        //2.Create connection to our database
        Connection conn = null;

        try {
        	conn = DriverManager.getConnection(DatabaseInfo.url, DatabaseInfo.username, DatabaseInfo.password);
           	}catch(SQLException e) {
           		throw new RuntimeException(e);
           	}
        
        //3 create query
        String queryOfAddress = "SELECT *  FROM Address";
        String queryOfState = "SELECT State FROM State where StateId = ?";
        String queryOfCountry = "SELECT Country FROM Country where CountryId = ?";

        PreparedStatement ps = null;
        PreparedStatement psGetState = null;
        PreparedStatement psGetCountry = null;

        ResultSet rs = null;      
        ResultSet rsGetState = null;
        ResultSet rsGetCountry = null;

        try {
        	ps = conn.prepareStatement(queryOfAddress);
        	psGetState = conn.prepareStatement(queryOfState);
        	psGetCountry = conn.prepareStatement(queryOfCountry);
        	
        	rs = ps.executeQuery();
        	//4. process the results
            while(rs.next()){
            	int addressId = rs.getInt("AddressId");
            	String street = rs.getString("Street");
            	String city = rs.getString("City");
            	String zip = rs.getString("Zip");
            	
            	
            	int stateId = rs.getInt("StateId");
            	psGetState.setInt(1,stateId);
            	rsGetState = psGetState.executeQuery();
            	String state = rsGetState.getString(stateId);
            	
            	
            	int countryId = rs.getInt("CountryId");
            	psGetCountry.setInt(1,countryId);
            	rsGetCountry = psGetCountry.executeQuery();
            	String country = rsGetCountry.getString(countryId);
            	
            	
            	Address a = new Address(street, city, state, zip, country);
            	address.add(a);
            }
        }catch(SQLException e) {
       		throw new RuntimeException(e);
       	}
        
        //5. clean up
        try {
            if(rs != null && !rs.isClosed()) {
        		rs.close();
        	}
            if(ps != null && !ps.isClosed()) {
        		ps.close();
        	}
        	if(conn != null && !conn.isClosed()) {
                conn.close();
        	}
        }catch(SQLException e) {
       		throw new RuntimeException(e);
       	}

		return address;
	}
	
}
