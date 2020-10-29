package application;
/**
The AccountDatabase class is the container class that represents an account database as an array of items.
Property of AccountDatabase is size.
Methods associated with AccountDatabase are find, grow, add, remove, deposit, withdrawal
sortByDateOpen, sortByLastName, printByDateOpen, printByLastName, and printAccounts 
@author Joshua Atienza, Kyle Lee
*/
public class AccountDatabase {
	private Account[] accounts = new Account[5];
	private int size;
	
	/**
	 * Finds a specific account in the database and returns its index in the array.
	 * Performs a linear search for the target account
	 * @param account The Account being searched for
	 * @return The index of the found account, -1 if item doesn't exist in database
	 */
	private int find(Account account) { 
		for (int i = 0; i < size; i++) {
			if(account.equals(accounts[i])) {
				return i;
			}
		}
		
		return -1;
	}
	
	
	/**
	 * Grows the capacity of the database by copying over object references from old array to new array
	 * Capacity of the database increases by 5 when grow() is called
	 */
	private void grow() { 

		Account[] temp = accounts;
		accounts = new Account[accounts.length+5];
		
		for (int i = 0; i < temp.length; i++) {
			accounts[i] = temp[i];
		}
		
	}
	
	
	/**
	 * Adds specified Account to database
	 * Account is added in the next immediately available index
	 * @param account The Account being added to the database
	 * @return true if add successful, false otherwise
	 */
	public boolean add(Account account) {
		
		if (find(account) != -1 || !account.getDateOpen().isValid()) {
			return false;
		}
		
		for (int i = 0; i < accounts.length; i++) {
			if (accounts[i] == null) {
				accounts[i] = account;
				break;
			}
		}
		size++;
		
		// Check whether the account database is full or not
		if (size%5 == 0) {
			grow();
		}
		
		return true;
	
	}
	
	
	/** 
	 * Removes the specified Account from database
	 * Replaces the removing element with the last element, and size field decreases by 1
	 * @param account The Account being removed from the database
	 * @return true if Account successfully removed, false otherwise
	 */
	public boolean remove(Account account) {
		
		int index = find(account);
		
		if(account == null || index == -1 || size < 1) {		//checks if account not found or account database is empty
			return false;
		}
		
		accounts[index] = accounts[size-1];						//replaces removing element with last element
		accounts[size-1] = null;
		
		size--;
		
		return true;
		
	}
	
	
	/**
	 * Deposits a specified amount into a designated account
	 * @param account The Account being deposited into
	 * @param amount The amount being deposited
	 * @return true if account exists and amount greater than 0, false otherwise
	 */
	public boolean deposit(Account account, double amount) {
		
		int index = find(account);
		
		if (account == null || index == -1 || amount < 0) {
			return false;
		}
		
		accounts[index].credit(amount);
		
		return true;
		
	} 
	
	
	/**
	 * Withdraws a specified amount from a designated account
	 * @param account The Account being withdrawn from
	 * @param amount The amount being withdrawn
	 * @return 0 if withdrawal successful, 1 if funds insufficient, -1 if account doesn't exist
	 */
	public int withdrawal(Account account, double amount) {
		
		int index = find(account);
		
		if (account == null || index == -1 || amount < 0) {		//check if account exists, invalid amount
			return -1;
		}
		
		else if (amount > accounts[index].getBalance()) {		//check if funds insufficient
			return 1;
		}
		
		else { 
			accounts[index].debit(amount);
			if (account instanceof MoneyMarket) {
				((MoneyMarket) account).setWithdrawals();
			}
		}
		
		return 0; 
	}
	
	
	/**
	 * Perform selection sort on accounts in database
	 * Sort by the dates the accounts were opened
	 */
	private void sortByDateOpen()  { // sort in ascending order
		int numAccounts = size;
		Date firstDateOpen;
		Date secondDateOpen;
		
		for (int i = 0; i < numAccounts-1; i++) {
			
			
			int min_idx = i;
			
			
			for (int j = i+1; j < numAccounts; j++) {
				
				// Retrieve last name of two that you are comparing
				firstDateOpen = accounts[j].getDateOpen();
				secondDateOpen = accounts[min_idx].getDateOpen();
				
				if (firstDateOpen.compareTo(secondDateOpen) < 0) {
					min_idx = j;
				}
			}
			
			Account temp = accounts[min_idx];
			accounts[min_idx] = accounts[i];
			accounts[i] = temp;
		}
		
	}
	
	/**
	 * Perform selection sort on accounts in database
	 * Sort by the last names of the account holders
	 */
	private void sortByLastName() { 
		
		int numAccounts = size;
		String firstHolder_lname;
		String secondHolder_lname;
		
		for (int i = 0; i < numAccounts-1; i++) {
			
			
			int min_idx = i;
			
			
			for (int j = i+1; j < numAccounts; j++) {
				
				// Retrieve last name of two that you are comparing
				firstHolder_lname = (accounts[j].getHolder()).get_lname();
				secondHolder_lname = (accounts[min_idx].getHolder()).get_lname();
				
				if (firstHolder_lname.compareTo(secondHolder_lname) < 0) {
					min_idx = j;
				}
			}
			
			Account temp = accounts[min_idx];
			accounts[min_idx] = accounts[i];
			accounts[i] = temp;

			
		}
	}
	
	
	/**
	 * Prints the list of accounts by the dates they were opened
	 * Includes starting balance and ending balance, with fees and interest
	 * @return output The list of accounts by the dates they were opened
	 */
	public String printByDateOpen() { 
		
		StringBuilder output = new StringBuilder("");
		
		if(size > 0) {
			sortByDateOpen();
			output.append("\n");
			output.append("--Printing statements by date opened--\n");
			//System.out.println();
			//System.out.println("--Printing statements by date opened--");
			
			
			for (int i = 0; i < size; i++) {
				
				output.append("\n");
				output.append(accounts[i].toString());
				output.append("\n");
				accounts[i].setBalance(accounts[i].monthlyInterest(), accounts[i].monthlyFee());
				output.append("-interest: $ " + String.format("%.2f", accounts[i].monthlyInterest()));
				output.append("\n");
				output.append("-fee: $ " + String.format("%.2f", accounts[i].monthlyFee()));
				output.append("\n");
				output.append("-new balance: $ " + (String.format("%.2f", accounts[i].getBalance())));
				output.append("\n");
				
				
				//System.out.println();
				//System.out.println(accounts[i].toString());
				//accounts[i].setBalance(accounts[i].monthlyInterest(), accounts[i].monthlyFee());
				//System.out.println("-interest: $ " + String.format("%.2f", accounts[i].monthlyInterest()));
				//System.out.println("-fee: $ " + String.format("%.2f", accounts[i].monthlyFee()));
				//System.out.println("-new balance: $ " + (String.format("%.2f", accounts[i].getBalance())));
				
			}
		
			output.append("--end of listing--\n");
			
		}
		
		else {
			output.append("Database is empty.\n");
		}
		
		return output.toString();
		
	}
	
	
	/** 
	 * Prints the list of accounts by the last names of the holders
	 * Includes starting balance and ending balance, with fees and interest
	 * @return output The list of accounts in the database by last names
	 */
	public String printByLastName() { 
		
		StringBuilder output = new StringBuilder("");
		
		if(size > 0) {
			
			sortByLastName();
			
			output.append("\n");
			output.append("--Printing statements by last name--\n");
			//System.out.println();
			//System.out.println("--Printing statements by last name--");
			
			for (int i = 0; i < size; i++) {
				
				output.append("\n");
				output.append(accounts[i].toString());
				output.append("\n");
				accounts[i].setBalance(accounts[i].monthlyInterest(), accounts[i].monthlyFee());
				output.append("-interest: $ " + String.format("%.2f", accounts[i].monthlyInterest()));
				output.append("\n");
				output.append("-fee: $ " + String.format("%.2f", accounts[i].monthlyFee()));
				output.append("\n");
				output.append("-new balance: $ " + (String.format("%.2f", accounts[i].getBalance())));
				output.append("\n");
				
				/*System.out.println();
				System.out.println(accounts[i].toString());
				accounts[i].setBalance(accounts[i].monthlyInterest(), accounts[i].monthlyFee());
				System.out.println("-interest: $ " + String.format("%.2f", accounts[i].monthlyInterest()));
				System.out.println("-fee: $ " + String.format("%.2f", accounts[i].monthlyFee()));
				System.out.println("-new balance: $ " + (String.format("%.2f", accounts[i].getBalance())));*/
			}
			
			output.append("--end of printing--\n");	
			
		}
		
		else {
			output.append("Database is empty.\n");
		}
		
		return output.toString();

	}
	
	/**
	 * Prints the list of accounts in the database
	 * @return output The list of accounts in the database
	 */
	public String printAccounts() { 
		
		StringBuilder output = new StringBuilder("");
		
		if(size > 0) {
			
			output.append("--Listing accounts in the database--\n");
			//System.out.println("--Listing accounts in the database--");

			for (int i = 0; i < size; i++) {
				output.append(accounts[i].toString());
				output.append("\n");
				//System.out.println(accounts[i].toString());
			}
			
			output.append("--end of listing--\n");
			//System.out.println("--end of listing--");	
			
		}

		else {
			output.append("Database is empty.\n");
			//System.out.println("Database is empty.");
		}
		
		return output.toString();
	}
	
	
	/**
	 * Prints the formatted list of accounts in the database
	 * @return output The formatted list of accounts in the database
	 */
	public String printFormattedAccounts() {
		
		StringBuilder output = new StringBuilder("");
		
		if(size > 0) {
			
			for(int i = 0; i < size; i++) {
				
				output.append(accounts[i].toStringExport());
				output.append("\n");
				
			}
			
		}
		
		else {
			
			output.append("Empty database.");
			
		}
		
		return output.toString();
		
	}
	

}
