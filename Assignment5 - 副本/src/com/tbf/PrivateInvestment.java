package com.tbf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PrivateInvestment extends Assets{
	private String code;
	private String type;
	private String label;
	private double quarterlyDividend;
	private double baseRateOfReturn;
	private double baseOmegaMeasure;
	private double totalValue;
	private double percentage;

	public PrivateInvestment(String code, String type, String label, double quarterlyDividend, double baseRateOfReturn,
			double baseOmegaMeasure, double totalValue) {
		super(type);
		this.code = code;
		this.type = type;
		this.label = label;
		this.quarterlyDividend = quarterlyDividend;
		this.baseRateOfReturn = baseRateOfReturn;
		this.baseOmegaMeasure = baseOmegaMeasure;
		this.totalValue = totalValue;
	}
	
	public String getPICode() {
		return code;
	}

	public void setPICode(String code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}



	public String getPILabel() {
		return label;
	}

	public void setPILabel(String label) {
		this.label = label;
	}

	public double getPIQuarterlyDividend() {
		return quarterlyDividend;
	}

	public void setPIQuarterlyDividend(double quarterlyDividend) {
		this.quarterlyDividend = quarterlyDividend;
	}

	public double getPIBaseRateOfReturn() {
		return baseRateOfReturn;
	}

	public void setPIBaseRateOfReturn(double baseRateOfReturn) {
		this.baseRateOfReturn = baseRateOfReturn;
	}

	public double getPIBaseOmegaMeasure() {
		return baseOmegaMeasure;
	}

	public void setPIBaseOmegaMeasure(double baseOmegaMeasure) {
		this.baseOmegaMeasure = baseOmegaMeasure;
	}

	public double getPITotalValue() {
		return totalValue;
	}

	public void setPITotalValue(double totalValue) {
		this.totalValue = totalValue;
	}
	

	public double getPercentage() {
		return percentage;
	}

	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}

	public double getOmegaMeasure() {
		double omega = 0.0;
		omega = this.getPIBaseOmegaMeasure() + Math.exp(-125500/this.getPITotalValue());
		return omega;
	}
	


	@Override
	public double  getRateOfReturn() {
		double rateOfReturn = 0.0;
		rateOfReturn = this.getAnnualreturn()/this.getTotalValue();
		return rateOfReturn * 100;
	}

	@Override
	public double getTotalValue() {
		double personalValue = this.getPITotalValue() * this.getPercentage();
		return personalValue;
	}

	@Override
	public double getAnnualreturn() {
		double expectedAnnualReturn = 0.0;
		expectedAnnualReturn = this.getPercentage() * ((this.getPIBaseRateOfReturn()/100 * this.getPITotalValue()) + (4 * this.getPIQuarterlyDividend()));
		
		return expectedAnnualReturn;
	}

	
	
	
}
