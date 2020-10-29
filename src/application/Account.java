package application;
/**
The Account class contains the properties and methods associated with the Account object.
Account is an abstract class and a superclass of Checking, Savings, and MoneyMarket.
Properties include holder, balance, and date the account was opened.
Methods include getHolder, getBalance, getDateOpen, setBalance, debit, credit, toString, and equals
@author Joshua Atienza, Kyle Lee
*/
public abstract class Account {

	private Profile holder;
	private double balance;
	private Date dateOpen;

	
	/**
	 * Creates an Account with the specified holder, balance, and dateOpen
	 * Constructor intended for the open account methods
	 * @param holder The profile of the account holder
	 * @param balance The balance of the account holder
	 * @param dateOpen The date the account was opened
	 */
	public Account(Profile holder, double balance, Date dateOpen) {
		this.holder = holder;
		this.balance = balance;
		this.dateOpen = dateOpen;
	}
	
	
	/**
	 * Creates an account with the specified holder
	 * Constructor intended for the withdraw, deposit, close methods
	 * @param holder The profile of the account holder
	 */
	public Account(Profile holder) {
		this.holder = holder;
	}

	
	/**
	 * Gets the profile of the account holder
	 * @return holder The profile of the account holder
	 */
	public Profile getHolder() {
		return holder;
	}
	
	
	/**
	 * Gets the balance of the account
	 * @return balance The balance of the account
	 */
	public double getBalance() {
		return balance;
	}
	
	
	/**
	 * Gets the date the account was opened
	 * @return dateOpen The date the account was opened
	 */
	public Date getDateOpen() {
		return dateOpen;
	}
	
	
	/**
	 * Updates the balance to include interest and fees
	 * @param interest The amount to be added to the account
	 * @param fee The amount to be subtracted from the account
	 */
	public void setBalance(double interest, double fee) {
		this.balance = this.balance + interest - fee;
	}
	
	
	/**
	 * Subtracts a designated amount from the balance
	 * @param amount The amount to be debited
	 */
	public void debit(double amount) {
		balance -= amount;
	}
	
	
	/**
	 * Adds a designated amount to the balance
	 * @param amount The amount to be credited
	 */
	public void credit(double amount) {
		balance += amount;
	}
	
	
	/**
	 * Converts Account object to String representation
	 * String representation follows the format "firstName LastName * $XX.XX*date"
	 * @return accountInfo The string representation of the Account
	 */
	@Override
	public String toString() { 
		
		String fname = this.holder.get_fname();
		String lname = this.holder.get_lname();
		String date = this.dateOpen.toString();
		
		String accountInfo = fname + " " + lname + "* $" + String.format("%.2f", balance) + "*" + date;
		
		return accountInfo;
		
	}
	
	/**
	 * Converts Account object to String representation
	 * String representation follows the format "firstName LastName * $XX.XX*date"
	 * @return accountInfo The string representation of the Account
	 */
	public String toStringExport() { 
		
		String fname = this.holder.get_fname();
		String lname = this.holder.get_lname();
		String date = this.dateOpen.toString();
		
		String accountInfo = fname + "," + lname + "," + String.format("%.2f", balance) + "," + date;
		
		return accountInfo;
		
	}
	
	
	/** 
	 * Checks if Account is equivalent to obj being compared to
	 * Checks if obj is instanceof Account and if all data fields are equivalent
	 * @param obj The object being compared to a particular Account
	 * @return true if Account is equivalent to object, false otherwise
	 */
	@Override 
	public boolean equals(Object obj){ 
		
		if (obj == this) { 
			return true;
		}
	  
		if (obj instanceof Account) { 
			Account currAcc = (Account) obj;
			return this.holder.equals(currAcc.getHolder()); 
		}
	  
		return false; 
	
	}
	 
	
	/**
	 * Abstract method named monthlyInterest
	 * Calculates monthlyInterest of a given account,
	 * Implementation specified in respective subclasses
	 * @return monthlyInterest The monthly interest calculated in respective subclasses
	 */
	public abstract double monthlyInterest();
	
	
	/**
	 * Abstract method named monthlyFee
	 * Calculates monthlyFee of a given account,
	 * Implementation specified in respective subclasses
	 * @return monthlyFee The monthly fee calculated in respective subclasses
	 */
	public abstract double monthlyFee();
	
}
