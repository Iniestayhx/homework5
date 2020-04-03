package com.tbf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class Assets {
	private String type;

	public Assets( String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public abstract double getTotalValue();
	public abstract double getAnnualreturn();
	public abstract double getRateOfReturn();

	//calculate everything in separate classes
	//methods that are not used in different classes for example just in deposit class just implement in deposit class not in stock class
	//
	
	
}
