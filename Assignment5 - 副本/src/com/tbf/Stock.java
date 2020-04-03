package com.tbf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Stock extends Assets{
    private String code;
	private String type;
	private String label;
	private double quarterlyDividend;
	private double baseRateOfReturn;
	private double betaMeasure;
	private String stockSymbol;
	private double sharePrice;
	private double shareNum;
	private double StockExpectedAnnualReturn;

	public Stock(String code, String type, String label, double quarterlyDividend, double baseRateOfReturn,
			double betaMeasure, String stockSymbol, double sharePrice) {
		super(type);
		this.code = code;
		this.type = type;
		this.label = label;
		this.quarterlyDividend = quarterlyDividend;
		this.baseRateOfReturn = baseRateOfReturn;
		this.betaMeasure = betaMeasure;
		this.stockSymbol = stockSymbol;
		this.sharePrice = sharePrice;
	}
	public String getStockCode() {
		return code;
	}
	public void setStockCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStockLabel() {
		return label;
	}
	public void setStockLabel(String label) {
		this.label = label;
	}
	public double getStockQuarterlyDividend() {
		return quarterlyDividend;
	}
	public void setQuarterlyDividend(double quarterlyDividend) {
		this.quarterlyDividend = quarterlyDividend;
	}
	public double getStockBaseRateOfReturn() {
		return baseRateOfReturn;
	}
	public void setStockBaseRateOfReturn(double baseRateOfReturn) {
		this.baseRateOfReturn = baseRateOfReturn;
	}
	public double getStockBetaMeasure() {
		return betaMeasure;
	}
	public void setStockBetaMeasure(double betaMeasure) {
		this.betaMeasure = betaMeasure;
	}
	public String getStockSymbol() {
		return stockSymbol;
	}
	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}
	public double getStockSharePrice() {
		return sharePrice;
	}
	public void setStockSharePrice(double sharePrice) {
		this.sharePrice = sharePrice;
	}

	public double getShareNum() {
		return shareNum;
	}
	public void setShareNum(double shareNum) {
		this.shareNum = shareNum;
	}

	@Override
	public double getTotalValue() {
		double total = this.getShareNum() * this.getStockSharePrice();
		return total; 
	}
	@Override
	public double getAnnualreturn() {
		this.StockExpectedAnnualReturn = this.getStockBaseRateOfReturn()/100 * this.getStockSharePrice() * this.getShareNum()+ this.getStockQuarterlyDividend() * this.getShareNum() * 4;
		return StockExpectedAnnualReturn;
	}
	@Override
	public double getRateOfReturn() {
		double rateOfReturn = this.getAnnualreturn()/this.getTotalValue();
		return rateOfReturn*100;
	}
	
}
