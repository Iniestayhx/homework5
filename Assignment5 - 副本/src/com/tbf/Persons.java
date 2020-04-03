package com.tbf;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Persons {
	private String personCode;
	private String type;
	private String scecode;
	private String firstName;
	private String lastName;
	private List<String> email = new ArrayList<>();
	private Address address;

	public Persons(String personCode, String firstName, String lastName, Address address, List<String> email) {
		super();
		this.personCode = personCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.address = address;

	}
	public Persons(String personCode, String type, String scecode, String firstName, String lastName, Address address,  List<String> email) {
		super();
		this.personCode = personCode;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.type = type;
		this.scecode = scecode;		
		this.address = address;

	}
	
	public String getPersonCode() {
		return personCode;
	}
	public String getType() {
		return type;
	}
	public String getScecode() {
		return scecode;
	}

	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public List<String> getEmail() {
		return email;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}
	public String getName() {
		return this.lastName +","+ this.firstName;
	}
	

}
	
	
	
