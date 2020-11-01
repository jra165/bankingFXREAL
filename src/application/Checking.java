package application;
/**
 * Checking is a subclass of Account with the properties and methods associated with the Checking object
 * Properties and methods inherited from Account class
 * Additional methods include monthlyInterest, monthlyFee, toString, and equals
 * @author Joshua Atienza, Kyle Lee
 *
 */
public class Checking extends Account {

	private boolean directDeposit;
	
	/**
	 * Creates a Checking account with specified holder, balance, dateOpen, directDeposit
	 * Constructor intended for open account methods
	 * @param holder The holder of the account
	 * @param balance The balance of the account
	 * @param dateOpen The date the account was opened
	 * @param directDeposit Whether or not the account allows direct deposit
	 */
	public Checking(Profile holder, double balance, Date dateOpen, boolean directDeposit) {
		super(holder, balance, dateOpen);
		this.directDeposit = directDeposit;
	}

	
	/** 
	 * Creates a Checking account with the specified holder
	 * Constructor intended for withdraw, deposit, close methods
	 * @param profile The profile of the holder
	 */
	public Checking(Profile profile) {
		super(profile);
	}


	/** 
	 * Calculates the monthly interest of Checking account
	 * @return monthlyInterest The monthly interest associated with a specific Checking account
	 */
	@Override
	public double monthlyInterest() {
		
		final double annualInterestRate = 0.0005;
		int period = 12;
		
		final double monthlyInterestRate = annualInterestRate/period;
		double monthlyInterest = getBalance() * monthlyInterestRate;
		
		return monthlyInterest;
		
	}

	
	/**
	 * Calculates the monthly fee of Checking account
	 * @return monthlyFee The monthly fee associated with a specific Checking account
	 */
	@Override
	public double monthlyFee() {
		
		double monthlyFee;
		final double threshold = 1500;
		final double checkingMonthlyFee = 25;
		
		if(directDeposit == true || getBalance() >= threshold) {
			monthlyFee = 0;
		}
		else {
			monthlyFee = checkingMonthlyFee;
		}
		
		return monthlyFee;
	}
	
	
	/**
	 * Converts Checking account to its String representation
	 * @return accountInfo The String representation of Checking object
	 */
	@Override
	public String toString() {
		
		String accountInfo;
		
		if (directDeposit) {
			accountInfo = "*Checking*" + super.toString() + "*direct deposit account*";
		}
		else {
			accountInfo = "*Checking*" + super.toString();
		}
		
		return accountInfo;
	}
	
	/**
	 * Converts Checking account to its modified String representation for export
	 * @return accountInfo The modified String representation of Checking object for export
	 */
	@Override
	public String toStringExport() {
		
		String accountInfo;
		
		if (directDeposit) {
			accountInfo = "C," + super.toStringExport() + ",true";
		}
		else {
			accountInfo = "C," + super.toStringExport() + ",false";
		}
		
		return accountInfo;
	}
	
	
	/**
	 * Checks if Checking is equivalent to obj being compared to.
	 * Checks if obj instanceof Checking and if all data fields are equivalent
	 * @param obj The object being compared to a particular Checking account
	 * @return true if Checking is equivalent to object, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj) && obj instanceof Checking) {
			return true;
		}
		return false;
	}
	
	
}
