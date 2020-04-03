package com.tbf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepositAccount extends Assets{
		private String  code;
		private String  type;	
		private String  lable;
		private double  apr;
		private double  accountValue;
		
		
		public DepositAccount(String code, String type, String lable, double apr) {
			super(type);
			this.code = code;
			this.type = type;
			this.lable = lable;
			this.apr = apr;
		}
		public String getDACode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getDALable() {
			return lable;
		}
		public void setDALable(String lable) {
			this.lable = lable;
		}
		

		public double getAccountValue() {
			return accountValue;
		}
		public void setAccountValue(double accountValue) {
			this.accountValue = accountValue;
		}
		public double getApr() {
			return apr;
		}
		public void setApr(double apr) {
			this.apr = apr;
		}

		public double getApy() {
			double apy;
			apy = Math.exp(this.getApr()/100)-1;
			return apy;
		}

		@Override
		public double getRateOfReturn() {
			double rate = this.getAnnualreturn()/this.getAccountValue();
			return rate*100;
		}
		@Override
		public double getTotalValue() {
			return this.getAccountValue();
		}
		@Override
		public double getAnnualreturn() {
			double annualReturn = this.getAccountValue()*this.getApy();
			return annualReturn;
		}
		
}
