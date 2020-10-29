package application;
/**
 * MoneyMarket is a subclass of Account with the properties and methods associated with the MoneyMarket object
 * Properties and methods inherited from Account class
 * Additional methods include monthlyInterest, monthlyFee, toString, equals
 * @author Joshua Atienza, Kyle Lee
 *
 */
public class MoneyMarket extends Account {
	
	private int withdrawals = 0;
	
	/**
	 * Creates a MoneyMarket account with the specified holder, balance, and date opened
	 * Constructor intended for open account methods
	 * @param holder The holder of the account
	 * @param balance The balance of the account
	 * @param dateOpen The date the account was opened
	 */
	public MoneyMarket(Profile holder, double balance, Date dateOpen) {
		super(holder, balance, dateOpen);
	}
	
	
	/** 
	 * Creates a MoneyMarket account with the specified holder
	 * Constructor intended for withdraw, deposit, and close methods
	 * @param profile The profile of the holder
	 */
	public MoneyMarket(Profile profile) {
		super(profile);
	}
	
	
	/**
	 * For case in which withdrawal is successful, we increment number of withdrawals
	 */
	public void setWithdrawals() {
		this.withdrawals++;
	}
	
	
	/** 
	 * Calculates the monthly interest of MoneyMarket account
	 * @return monthlyInterest The monthly interest associated with a specific MoneyMarket account
	 */
	@Override
	public double monthlyInterest() {
		
		final double annualInterestRate = 0.0065;
		int period = 12;
		final double monthlyInterestRate = annualInterestRate/period;
		double monthlyInterest = getBalance() * monthlyInterestRate;
		
		return monthlyInterest;
		
	}

	/**
	 * Calculates the monthly fee of MoneyMarket account
	 * @return monthlyFee The monthly fee associated with a specific MoneyMarket account
	 */
	@Override
	public double monthlyFee() {
		
		double monthlyFee;
		final double threshold = 2500;
		final double moneyMarketMonthlyFee = 12;
		
		if(getBalance() >= threshold) {
			if (withdrawals > 6) {
				monthlyFee = moneyMarketMonthlyFee;
			}
			else {
				monthlyFee = 0;
			}
		}
		else {
			monthlyFee = moneyMarketMonthlyFee; 
		}

		return monthlyFee;
	}
	
	/**
	 * Decreases the balance by a specified amount, and increases number of withdrawals by 1
	 * @param amount The amount to be debited from the MoneyMarket account
	 */
	@Override
	public void debit(double amount) { //decrease the balance by amount
		
		super.debit(amount);
		setWithdrawals();
		
	}
	
	/**
	 * Converts MoneyMarket account to its String representation
	 * @return accountInfo The String representation of MoneyMarket object
	 */
	@Override
	public String toString() {
		
		String accountInfo;
		
		if (withdrawals == 1) {
			accountInfo = "*Money Market*" + super.toString() + "*" + this.withdrawals + " withdrawal*";
		}
		else {
			accountInfo = "*Money Market*" + super.toString() + "*" + this.withdrawals + " withdrawals*";
		}
		
		return accountInfo;
	}
	
	/**
	 * Converts MoneyMarket account to its modified String representation
	 * @return accountInfo The modified String representation of MoneyMarket object
	 */
	@Override
	public String toStringExport() {
		
		String accountInfo;
		
		accountInfo = "M," + super.toStringExport() + "," + this.withdrawals;
		
		return accountInfo;
	}
	
	/**
	 * Checks if MoneyMarket is equivalent to obj being compared to.
	 * Checks if obj instanceof MoneyMarket and if all data fields are equivalent
	 * @param obj The object being compared to a particular MoneyMarket account
	 * @return true if MoneyMarket is equivalent to object, false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj) && obj instanceof MoneyMarket) {
			return true;
		}
		return false;
	}

}
