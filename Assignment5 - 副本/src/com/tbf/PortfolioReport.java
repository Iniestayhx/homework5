package com.tbf;

import java.io.File;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Map.Entry;


public class PortfolioReport {
	public static LinkedHashMap<String, Persons> getPersonsSummaries() {
		LinkedHashMap<String, Persons> personMap = new LinkedHashMap<String, Persons>();

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
        String queryOfAddress = "SELECT *  FROM Person";

        PreparedStatement ps = null;
        ResultSet rs = null;      


        try {
        	ps = conn.prepareStatement(queryOfAddress);
        	rs = ps.executeQuery();
        	//4. process the results
            while(rs.next()){
            	String personCode = rs.getString("PersonCode");
            	String firstName = rs.getString("FirstName");
            	String lastName = rs.getString("LastName");
            	double apr = rs.getDouble("APR");
            	String brokerType = rs.getString("BrokerType");
            	
            	String street = rs.getString("Street");
            	String city = rs.getString("City");	
            	String state = rs.getString("State");
            	String zip = rs.getString("Zip");
            	String country = rs.getString("Country");
	    		Address address = new Address(street, city, state, zip, country);
	    		String emailAddresses[] = rs.getString("Email").split(",");
	    		List<String> email = new ArrayList<>();
    			for(String e : emailAddresses) {
    				email.add(e);
    			}
            	if(brokerType.equals(null)) {
            		Persons p = new Persons( personCode,  firstName,  lastName, address, email);
            		personMap.put(personCode, p);
            	}else {
            		String scecode = rs.getString("BrokerCode");
            		Persons p = new Persons( personCode,  brokerType,  scecode,  firstName,  lastName,  address,  email);
            		personMap.put(personCode, p);
            	}

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
		return personMap;
	}
	
	
	public static LinkedHashMap<String, DepositAccount> getDepositAccountSummaries() {
		LinkedHashMap<String, DepositAccount> depositAccountMap = new LinkedHashMap<String, DepositAccount>();
		
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
        String query = "SELECT *  FROM Asset wherer AssetType = 'D'";

        PreparedStatement ps = null;
        ResultSet rs = null;      


        try {
        	ps = conn.prepareStatement(query);
        	rs = ps.executeQuery();
        	//4. process the results
            while(rs.next()){
            	String code = rs.getString("AssetCode");
            	String type = rs.getString("AssetType");
            	String label = rs.getString("Label");
            	double apr = rs.getDouble("APR");
            	DepositAccount d = new DepositAccount( code,  type,  label,  apr);
            	depositAccountMap.put(code, d);
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
		return depositAccountMap;
	}
	
	public static LinkedHashMap<String, PrivateInvestment> getPrivateInvestmentSummaries() {
		LinkedHashMap<String, PrivateInvestment> privateInvestmentMap = new LinkedHashMap<String, PrivateInvestment>();

		
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
        String query = "SELECT *  FROM Asset wherer AssetType = 'P'";

        PreparedStatement ps = null;
        ResultSet rs = null;      


        try {
        	ps = conn.prepareStatement(query);
        	rs = ps.executeQuery();
        	//4. process the results
            while(rs.next()){
            	String code = rs.getString("AssetCode");
            	String type = rs.getString("AssetType");
            	String label = rs.getString("Label");
            	double quarterlyDividend = rs.getDouble("QuarterlyDividend");
            	double baseRateOfReturn = rs.getDouble("BaseRateOfReturn");
            	double baseOmegaMeasure = rs.getDouble("BaseOmegaMeasure");
            	double totalValue = rs.getDouble("TotalValue");

            	PrivateInvestment p = new PrivateInvestment(code, type, label, quarterlyDividend, baseRateOfReturn, baseOmegaMeasure, totalValue);
            	privateInvestmentMap.put(code, p);
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
		return privateInvestmentMap;
	}
	public static LinkedHashMap<String, Stock> getStockSummaries() {
		LinkedHashMap<String, Stock> stockMap = new LinkedHashMap<String, Stock>();

		
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
        String query = "SELECT *  FROM Asset wherer AssetType = 'S'";

        PreparedStatement ps = null;
        ResultSet rs = null;      


        try {
        	ps = conn.prepareStatement(query);
        	rs = ps.executeQuery();
        	//4. process the results
            while(rs.next()){
            	String code = rs.getString("AssetCode");
            	String type = rs.getString("AssetType");
            	String label = rs.getString("Label");
            	double quarterlyDividend = rs.getDouble("QuarterlyDividend");
            	double baseRateOfReturn = rs.getDouble("BaseRateOfReturn");
            	double betaMeasure = rs.getDouble("BetaMeasure");
            	String stockSymbol = rs.getString("StockSymbol");
            	double sharePrice = rs.getDouble("SharePrice");

            	Stock s = new Stock( code,  type, label,  quarterlyDividend,  baseRateOfReturn, betaMeasure, stockSymbol,sharePrice);
            	stockMap.put(code, s);
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
		return stockMap;
	}
	public static LinkedHashMap<String, Portfolio> getPortfolioSummaries() {
		
		LinkedHashMap<String, Portfolio> portfolioMap = new LinkedHashMap<String, Portfolio>();

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
        String query = "SELECT *  FROM Portfolio";
        String queryOfPerson = "SELECT *  FROM Person where PersonId = ?";
        
        PreparedStatement ps = null;
        ResultSet rs = null; 
        
        PreparedStatement psOfPerson = null;
        ResultSet rsOfPerson = null;     
        try {
        	ps = conn.prepareStatement(query);
        	rs = ps.executeQuery();
        	
        	psOfPerson = conn.prepareStatement(queryOfPerson);
      
        	//4. process the results
            while(rs.next()){

            	String portfolioCode = rs.getString("PortfolioCode");
            	String ownerId = rs.getString("PortfolioOwnerId");
            	String managerId = rs.getString("PortfolioManagerId");
            	String beneficiaryId = rs.getString("PortfolioBeneficiaryId");
            	if(beneficiaryId.equals(null)) {
            		psOfPerson.setString(1, ownerId);
	            	rsOfPerson = psOfPerson.executeQuery();
	            	String ownerCode = rsOfPerson.getString("PersonCode");
	            	
	              	psOfPerson.setString(1, managerId);
	            	rsOfPerson = psOfPerson.executeQuery();
	            	String managerCode = rsOfPerson.getString("PersonCode");
	            	
	            	
	            	Portfolio p = new Portfolio( portfolioCode,  ownerCode,  managerCode);
	            	portfolioMap.put(portfolioCode, p);
            	}else {         
            		psOfPerson.setString(1, ownerId);
	            	rsOfPerson = psOfPerson.executeQuery();
	            	String ownerCode = rsOfPerson.getString("PersonCode");
	            	
	              	psOfPerson.setString(1, managerId);
	            	rsOfPerson = psOfPerson.executeQuery();
	            	String managerCode = rsOfPerson.getString("PersonCode");
	            	
	              	psOfPerson.setString(1, beneficiaryId);
	            	rsOfPerson = psOfPerson.executeQuery();
	            	String beneficiaryCode = rsOfPerson.getString("PersonCode");
	            	
	            	Portfolio p = new Portfolio( portfolioCode,  ownerCode,  managerCode, beneficiaryCode);
	            	portfolioMap.put(portfolioCode, p);
            	}

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
		return portfolioMap;
	}
	public static void main(String[]args) {
		
		
		LinkedHashMap<String, Persons> personMap = getPersonsSummaries();
		LinkedHashMap<String, DepositAccount> depositAccountMap = getDepositAccountSummaries();
		LinkedHashMap<String, PrivateInvestment> privateInvestmentMap = getPrivateInvestmentSummaries();
		LinkedHashMap<String, Stock> stockMap = getStockSummaries();
		LinkedHashMap<String, Assets> assetMap = new LinkedHashMap<String, Assets>();
		assetMap.putAll(depositAccountMap);
		assetMap.putAll(privateInvestmentMap);
		assetMap.putAll(stockMap);

		LinkedHashMap<String, Portfolio> potfolioMap = getPortfolioSummaries();
		//Create AssetList
	
		
	    	
	    	

		
		Scanner s1 = null;
		try {
			s1 = new Scanner(new File("data/Portfolios.dat"));
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
    	@SuppressWarnings("unused")
		int size3 = Integer.parseInt(s1.nextLine());

    	while(s1.hasNext()) {
    		String line = s1.nextLine();
    		String token[] = line.split(";");
    		String portfolioCode = token[0];
    		String ownerCode = token[1];
    		String managerCode = token[2];
    		String assetsList[] = null;
    		@SuppressWarnings("unused")
			int assetSize = 0;

        	Map<String, Double> portfolioAssetsMap = new IdentityHashMap<String, Double>();
    		if(token.length == 3) {

    			Portfolio portfolio = new Portfolio(portfolioCode,ownerCode,managerCode);
    			portfolio.setAssetsMap(assetMap);
    			portfolio.setPersonsMap(personMap);
    			portfolio.setPersonJudge();

    			potfolioMap.put(portfolioCode, portfolio);
    			
    		}
    		if(token.length == 4) {
        		String beneficiaryCode = token[3];
    			Portfolio portfolio = new Portfolio(portfolioCode,ownerCode,managerCode,beneficiaryCode);
    			portfolio.setPersonsMap(personMap);
    			portfolio.setPersonJudge();

    			potfolioMap.put(portfolioCode, portfolio);
    			
    			
    		}
    		if(token.length == 5){
        		String beneficiaryCode = token[3];
        		assetsList = token[4].split(",");
        		
        		for(String aAsset : assetsList) {
        			String asset[] = aAsset.split(":");
        			portfolioAssetsMap.put(new String(asset[0]), Double.parseDouble(asset[1]));

        		}

        		Portfolio portfolio = new Portfolio(portfolioCode,ownerCode,managerCode,beneficiaryCode,portfolioAssetsMap);
    			portfolio.setAssetsMap(assetMap);
    			portfolio.setPersonsMap(personMap);
    			portfolio.culculation();
    			portfolio.setPersonJudge();
    			portfolio.setFeeAndCommision();		
    			portfolio.setRiskMeasure();
        		potfolioMap.put(portfolioCode, portfolio);
    		}
    	}



		//Print Portfolio Summary Report
		System.out.println("Portfolio Summary Report\n===============================================================================================================================");
		System.out.printf("%s%16s%20s%20s%20s%20s%20s%20s\n","Portfolio","Owner","Manager","Fees","Commisions","Weighted Risk","Return","Total");
		double fee = 0.0;
		double commision = 0.0;
		double Return = 0.0;
		double total = 0.0;
		for(Entry<String, Portfolio> p: potfolioMap.entrySet()) {
			System.out.printf("%s%20s%20s%20.2f%20.2f%20.4f%20.2f%20.2f\n",p.getValue().getPortfolioCode() 
					, p.getValue().getOwnerName() 
					, p.getValue().getManagerName()
					, p.getValue().getFee()
					, p.getValue().getCommision()
					, p.getValue().getAggregateRiskMeasure()
					, p.getValue().getTotalRetuenOfThePortfolio()
					, p.getValue().getTotalValueOfThePortfolio()
					);
			fee += p.getValue().getFee();
			commision += p.getValue().getCommision();
			Return +=p.getValue().getTotalRetuenOfThePortfolio();
			total += p.getValue().getTotalValueOfThePortfolio();
		}
		System.out.println("-------------------------------------------------------------------------");
		System.out.printf("%50s%15.2f%20.2f%37.2f%20.2f\n","Totals ",fee,commision,Return,total);
		
		
		
		
		//Print Portfolio Details
		System.out.println("Portfolio Details\n================================================================================================================");
		
		for(Entry<String, Portfolio> p: potfolioMap.entrySet()){

			System.out.println("Portfolio " + p.getKey());
			System.out.println("------------------------------------------");
			for(Entry<String, Persons> p1: personMap.entrySet()){
				if(p.getValue().getOwnerCode().equals(p1.getKey())) {
					System.out.println("Owner:");
					System.out.println(p1.getValue().getName());	
					System.out.println(p1.getValue().getEmail());
					System.out.println(p1.getValue().getAddress().toString());
				}
			}
			for(Entry<String, Persons> p1: personMap.entrySet()){
				if(p.getValue().getManagerCode().equals(p1.getKey())) {
					System.out.println("Manager:");
					System.out.println(p1.getValue().getName());
				}
			}
			for(Entry<String, Persons> p1: personMap.entrySet()){
				if(p.getValue().getBeneficiaryCode() != null) {
					if(p.getValue().getBeneficiaryCode().equals(p1.getKey())) {
						System.out.println("Beneficiary:");
						System.out.println(p1.getValue().getName());	
						if(p1.getValue().getEmail().size() != 0) {
							System.out.println(p1.getValue().getEmail());
						}
						System.out.println(p1.getValue().getAddress().toString());
					}
				}
			}
			System.out.println("Assets");
			if(p.getValue().getAssetNumber()!=0) {
				p.getValue().culculation();
				System.out.printf("%s%25s%15s%12s%16s%12s\n","Code","Asset","Return Rate","Risk","Annual Return","Value");
				double risk = p.getValue().getAggregateRiskMeasure();
				double totalReturn = p.getValue().getTotalRetuenOfThePortfolio();
				double totalValue = p.getValue().getTotalValueOfThePortfolio();
				for(Entry<String, Double> pam : p.getValue().getPortfolioAssetsMap().entrySet()) {
					for(Entry<String, Assets> a: assetMap.entrySet()) {
						if(pam.getKey().equals(a.getKey())) {
							if(a.getValue().getType().equals("Stock")) {
								Stock stc = (Stock) a.getValue();
								System.out.printf("%s%25s%10.2f%s%13.2f%13.2f%16.2f\n",a.getKey(),stc.getStockLabel(),stc.getRateOfReturn(),"%",stc.getStockBetaMeasure(),stc.getAnnualreturn(),stc.getTotalValue());
							}
							if(a.getValue().getType().equals("privateInvestment")) {
								PrivateInvestment pi = (PrivateInvestment) a.getValue();
								System.out.printf("%s%25s%10.2f%s%13.2f%13.2f%16.2f\n",a.getKey(),pi.getPILabel(),pi.getRateOfReturn(),"%",pi.getOmegaMeasure(),pi.getAnnualreturn(),pi.getTotalValue());
							}
							if(a.getValue().getType().equals("depositAccount")) {
								DepositAccount da = (DepositAccount) a.getValue();
								System.out.printf("%s%25s%10.2f%s%13.2f%13.2f%16.2f\n",a.getKey(), da.getDALable(), da.getRateOfReturn(),"%",0.00,da.getAnnualreturn(),da.getAccountValue());
							}
						}
					}
//					System.out.println("\n");
				}
				System.out.println("                    --------------------------------------------");
				System.out.printf("%30s%25.4f%14.2f%15.2f\n\n\n","Totals",risk,totalReturn,totalValue);
			}else {
				System.out.println( 
						"Code   Asset       Return Rate    Risk  Annual Return       Value\r\n" + 
						"                      --------------------------------------------\r\n" + 
						"                  Totals        0.0000  $        0.00  $        0.00");
			}
    	}
	}
}

