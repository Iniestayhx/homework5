package com.tbf;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


public class Portfolio {
	private String portfolioCode;
	private String ownerCode;
	private String managerCode;
	private String beneficiaryCode;
	private Map<String, Double> portfolioAssetsMap;
	private Map<String, Assets> assetsMap;
	private Map<String, Persons> personsMap;
	private int assetNumber;
	private String ownerName;
	private String managerName;
	private double fee;
	private double commision;
	private double totalValueOfThePortfolio;
	private double totalRetuenOfThePortfolio;
	private double aggregateRiskMeasure;
	public Portfolio(String portfolioCode, String ownerCode, String managerCode) {
		super();
		this.portfolioCode = portfolioCode;
		this.ownerCode = ownerCode;
		this.managerCode = managerCode;
	}

	public Portfolio(String portfolioCode, String ownerCode, String managerCode, String beneficiaryCode) {
		super();
		this.portfolioCode = portfolioCode;
		this.ownerCode = ownerCode;
		this.managerCode = managerCode;
		this.beneficiaryCode = beneficiaryCode;
	}

	
	public Portfolio(String portfolioCode, String ownerCode, String managerCode, String beneficiaryCode,Map<String, Double> portfolioAssetsMap) {
		this.portfolioCode = portfolioCode;
		this.ownerCode = ownerCode;
		this.managerCode = managerCode;
		this.beneficiaryCode = beneficiaryCode;
		this.portfolioAssetsMap = portfolioAssetsMap;
		this.assetNumber = portfolioAssetsMap.size();
		
	}
	
	
	public int getAssetNumber() {
		return assetNumber;
	}


	public Map<String, Assets> getAssetsMap() {
		return assetsMap;
	}

	public void setAssetsMap(Map<String, Assets> assetsMap) {
		this.assetsMap = assetsMap;
	}

	public Map<String, Persons> getPersonsMap() {
		return personsMap;
	}

	public void setPersonsMap(Map<String, Persons> personsMap) {
		this.personsMap = personsMap;
	}

	public String getPortfolioCode() {
		return portfolioCode;
	}
	public String getOwnerCode() {
		return ownerCode;
	}
	public String getManagerCode() {
		return managerCode;
	}
	public String getBeneficiaryCode() {
		return beneficiaryCode;
	}
	
	
	
	
	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getManagerName() {
		return managerName;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}
	

	public double getTotalValueOfThePortfolio() {
		return totalValueOfThePortfolio;
	}

	public double getTotalRetuenOfThePortfolio() {
		return totalRetuenOfThePortfolio;
	}
	
	
	public Map<String, Double> getPortfolioAssetsMap() {
		return portfolioAssetsMap;
	}

	public double getFee() {
		return fee;
	}

	public double getCommision() {
		return commision;
	}

	

	public double getAggregateRiskMeasure() {
		return aggregateRiskMeasure;
	}

	
	//get owner name and manager name
	public void setPersonJudge() {
		for(Entry<String,Persons> person : this.personsMap.entrySet()) {
			if(this.ownerCode.equals(person.getKey())){
				this.ownerName = person.getValue().getName();
			}
			if(this.managerCode.equals(person.getKey())) {
				this.managerName = person.getValue().getName();
			}
		}
	}

	//culculate fee and commision
	public void setFeeAndCommision() {
		for(Entry<String,Persons> person : this.personsMap.entrySet()) {
			if(this.managerCode.equals(person.getKey())) {
				if(person.getValue().getType().equals("Expert")) {
					this.commision = this.getTotalRetuenOfThePortfolio() * 0.0375;
					this.fee = 0.00;
					
				}else if(person.getValue().getType().equals("Junior broker")) {
					this.fee = 75.00 *this.getPortfolioAssetsMap().size();
					this.commision = this.getTotalRetuenOfThePortfolio() * 0.0125;
				}
			}
		}
	}

	
	//culculate totalvalue and totalreturn
	public void culculation() {
		double totalReturn=0.0;
		double totalValue=0.0;
		for(Entry<String, Double> portfolioAsset: portfolioAssetsMap.entrySet()) {
			for(Entry<String, Assets> aAsset : assetsMap.entrySet()) {
				if(portfolioAsset.getKey().equals( aAsset.getKey())) {
					if(aAsset.getValue().getType().equals("Stock")) {
						Stock stc = (Stock) aAsset.getValue();
						stc.setShareNum(portfolioAsset.getValue());
						double totalValueOfStock = stc.getTotalValue();
						double sExpectedAnnualReturn = stc.getAnnualreturn();
						totalValue += totalValueOfStock;
						totalReturn += sExpectedAnnualReturn;
					}else if(aAsset.getValue().getType().equals("privateInvestment")) {
						PrivateInvestment pi = (PrivateInvestment) aAsset.getValue();
						pi.setPercentage(portfolioAsset.getValue()/100);
						double pExpectedAnnualReturn = pi.getAnnualreturn();
						double personalTotalValue = pi.getTotalValue();
						totalValue += personalTotalValue;
						totalReturn += pExpectedAnnualReturn;
					}else if(aAsset.getValue().getType().equals("depositAccount")) {
						DepositAccount da = (DepositAccount) aAsset.getValue();
						da.setAccountValue(portfolioAsset.getValue());		
						double totalValueOfDA = da.getTotalValue();
						double dExpectedAnnualReturn = da.getAnnualreturn();
						totalValue += totalValueOfDA;
						totalReturn += dExpectedAnnualReturn;
					}
				}
			}
		}
		this.totalValueOfThePortfolio = totalValue;
		this.totalRetuenOfThePortfolio = totalReturn;
	}
	
	
	
	//culculate risk measure
	public void setRiskMeasure() {
		
		double riskMeasure = 0.0;
		for(Entry<String, Double> portfolioAsset: portfolioAssetsMap.entrySet()) {
			for(Entry<String, Assets> aAsset : assetsMap.entrySet()) {
				if(portfolioAsset.getKey().equals( aAsset.getKey())) {
					if(aAsset.getValue().getType().equals("Stock")) {
						Stock stc = (Stock) aAsset.getValue();

						double totalValueOfStock = stc.getTotalValue();
						double beta = stc.getStockBetaMeasure();

						riskMeasure += beta * (totalValueOfStock/this.getTotalValueOfThePortfolio());
					}else if(aAsset.getValue().getType().equals("privateInvestment")) {
						PrivateInvestment pi = (PrivateInvestment) aAsset.getValue();
						
						double omega = pi.getOmegaMeasure();
						
						double pTotalValue =pi.getTotalValue();

						riskMeasure += omega * (pTotalValue/this.getTotalValueOfThePortfolio());
					}
				}
			}
		}
		this.aggregateRiskMeasure = riskMeasure;
	}
}
