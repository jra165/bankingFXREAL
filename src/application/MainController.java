package application;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

/**
 * The MainController class is the class that integrates logic of application into the UI
 * UI components shown below
 * Methods include clearFields, closeAcc, depositFunds, exportFile, importFile, openAcc,
 * printAcc, printByDate, printByLastName, selectSavings, selectMoneyMarket, selectChecking, 
 * and withdrawFunds — all of which are described below
 * @author Joshua Atienza, Kyle Lee
 *
 */
public class MainController {
	
    @FXML
    private CheckBox directDep, isLoyal;

    @FXML
    private RadioButton checkingOpenClose,savingsOpenClose,moneyMarketOpenClose;
    
    @FXML
    private ToggleGroup tgOpenClose, tgDepositWithdraw;

    @FXML
    private Button open, close, clear;

    @FXML
    private TextField balance, fName_OpenClose, lName_OpenClose, month, day, year;

    @FXML
    private Button deposit, withdraw;

    @FXML
    private RadioButton checkDepWith, savingsDepWith, monMarketDepWith;

    @FXML
    private TextField amount, fName_DepWith, lName_DepWith;

    @FXML
    private MenuItem importFile;
    
    @FXML
    private TextArea messageArea;
    
    public static AccountDatabase db = new AccountDatabase();
    
    /**
     * Clears user-inputed fields in the UI
     * @param event The semantic event that indicates user clicked 'Clear' button
     */
    @FXML
    void clearFields(ActionEvent event) {
    	fName_OpenClose.clear();
    	lName_OpenClose.clear();
    	month.clear();
    	day.clear();
    	year.clear();
    	balance.clear();
    	tgOpenClose.selectToggle(null);
    	directDep.setSelected(false);
    	directDep.setDisable(false);
    	isLoyal.setSelected(false);
    	isLoyal.setDisable(false);
    }
    
    /**
     * Closes an account inside the AccountDatabase
     * @param event The semantic event that indicates a user clicked 'Close Account'
     */
    @FXML
    void closeAcc(ActionEvent event) {
    	
    	try {
    		
    		// Create instances of profile and date based on text entry boxes
        	Profile person = new Profile(fName_OpenClose.getText(), lName_OpenClose.getText());
        	boolean closed = false;
        	
        	String accType = ((RadioButton) tgOpenClose.getSelectedToggle()).getText();
        	
        	//remove account according to account type
        	switch (accType) {
        	case "Checking":
        		Account currCheckAcc = new Checking(person);
        		closed = db.remove(currCheckAcc);
        		break;
        	case "Savings":
        		Account currSavingsAcc = new Savings(person);
        		closed = db.remove(currSavingsAcc);
        		break;
        	case "Money Market":
        		Account currMoneyMarketAcc = new MoneyMarket(person);
        		closed = db.remove(currMoneyMarketAcc);
        		break;
        	}
        	
        	if (closed) {
        		messageArea.appendText("Account closed and removed from the database.\n");
        	}
        	else {
        		messageArea.appendText("Account does not exist.\n");
        	}
    	}
    	catch (ArrayIndexOutOfBoundsException e) {
    		messageArea.appendText("ArrayIndexOutOfBoundsException error.\n");
    	}
    	catch (NullPointerException e) {
    		messageArea.appendText("NullPointerException error.\n");
    	}
    	
    		
    	
    }
    
    
    /**
     * Deposits funds into a specific account within AccountDatabase
     * @param event The semantic event that indicates a user has clicked "Deposit" button
     */
    @FXML
    void depositFunds(ActionEvent event) {
    	try {
    		
    		Profile person = new Profile(fName_DepWith.getText(), lName_DepWith.getText());
    		double inpBalance = Double.parseDouble(amount.getText());
    		boolean deposited = false;
    		
        	if(inpBalance < 0) {
        		throw new IllegalArgumentException("No negative numbers, please enter a valid balance.\n");
        	}
    		
    		String accType = ((RadioButton) tgDepositWithdraw.getSelectedToggle()).getText();
    		
    		//deposits funds in specified account by according to account type
    		switch(accType) {
    		case "Checking":
    			Account currCheckingAcc = new Checking(person);
    			deposited = db.deposit(currCheckingAcc, inpBalance);
    			break;
    		case "Savings":
    			Account currSavingsAcc = new Savings(person);
    			deposited = db.deposit(currSavingsAcc, inpBalance);
    			break;
    		case "Money Market":
    			Account currMoneyMarketAcc = new MoneyMarket(person);
    			deposited = db.deposit(currMoneyMarketAcc, inpBalance);
    			break;
    		}
    		
    		
    		if (deposited) {
    			messageArea.appendText(String.format("%.2f", inpBalance) + " deposited to account.\n");
    		}
    		else {
    			messageArea.appendText("Account does not exist.\n");
    		}
    		
    		
    	}
    	catch(NumberFormatException e) {
    		messageArea.appendText("Number format exception, enter a valid amount to deposit.\n");
    	}
    	catch(NullPointerException e) {
    		messageArea.appendText("Select a valid account type to deposit into.\n");
    	}
    	catch (IllegalArgumentException e) {
    		messageArea.appendText("No negative numbers, please enter a valid balance.\n");
    	}
    }
    
    /**
     * Exports all accounts in AccountDatabase to a formatted text file
     * @param event The semantic event that indicates a user clicked 'Export' button
     */
    @FXML
    void exportFile(ActionEvent event) {
    	FileChooser chooser = new FileChooser();
		chooser.setTitle("Open Target File for the Export");
		chooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
				new ExtensionFilter("All Files", "*.*"));
		Stage stage = new Stage();
		
		//get the reference of the target file
		File targeFile = chooser.showSaveDialog(stage); 
		
		
		//writes accounts to text file and exports
		try {
			
			BufferedWriter bf = new BufferedWriter(new FileWriter(targeFile));
			bf.write(db.printFormattedAccounts());
			bf.flush();
			bf.close();
			
			messageArea.appendText("File successfully exported.\n");
			
		} catch (IOException e) {
    		messageArea.appendText("Unable to export transactions to file.\n");
		}
		catch (NullPointerException e) {
			messageArea.appendText("File not selected, unable to export transactions to file.\n");
		}
		
		
    }
    
    
    /**
     * Imports a formatted (comma separated) text file of accounts
     * Loads accounts into the AccountDatabase
     * @param event The semantic event that indicates a user clicked "Import" button
     * @throws FileNotFoundException Occurs if specified pathname has failed
     */
    @FXML
    void importFile(ActionEvent event) throws FileNotFoundException{
    	FileChooser chooser = new FileChooser();
		chooser.setTitle("Open Source File for the Import");
		chooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.txt"),
				new ExtensionFilter("All Files", "*.*"));
		Stage stage = new Stage();
		
		//get the reference of the source file
		File sourceFile = chooser.showOpenDialog(stage); 
		
		try {
			
			messageArea.appendText("Importing from file: " + sourceFile.toString() + "...\n\n");
			Scanner reader;
			reader = new Scanner(sourceFile);
			
			Scanner scanner = null;
			while(reader.hasNext()) {
					
				scanner = new Scanner(reader.nextLine());
				scanner.useDelimiter(",");
				
				String fName;
				String lName;
				double balance;
				String date ;
				boolean special;
				int withdrawals;
				
				try {
					
					while(scanner.hasNext()) {
						
						String accountType = scanner.next();
						
						//add Savings account to db
						if(accountType.equals("S")) {
								
							fName = scanner.next();
							lName = scanner.next();
							balance = scanner.nextDouble();
							date = scanner.next();
							special = scanner.nextBoolean();
							
							Profile profile = new Profile(fName, lName);
							Date dateOpen = new Date(date);
							
							Savings savings = new Savings(profile, balance, dateOpen, special);
							db.add(savings);
								
								
						}
						
						//Add Checking account to db
						if(accountType.equals("C")) {
							
							fName = scanner.next();
							lName = scanner.next();
							balance = scanner.nextDouble();
							date = scanner.next();
							special = scanner.nextBoolean();
							
							Profile profile = new Profile(fName, lName);
							Date dateOpen = new Date(date);
							
							Checking checking = new Checking(profile, balance, dateOpen, special);
							db.add(checking);
							
							
						}	
						
						//Add Money Market account to db
						if(accountType.equals("M")) {
							
							fName = scanner.next();
							lName = scanner.next();
							balance = scanner.nextDouble();
							date = scanner.next();
							withdrawals = scanner.nextInt();
							
							Profile profile = new Profile(fName, lName);
							Date dateOpen = new Date(date);
							
							MoneyMarket mm = new MoneyMarket(profile, balance, dateOpen);
							
							for(int i = 0; i < withdrawals; i++) {
								mm.setWithdrawals();
							}
							
							db.add(mm);
							
							
						}
						
				 
					}
					
				} catch(InputMismatchException e) {
					messageArea.appendText("Invalid input data type, account not created.\n");
				}
				catch(NoSuchElementException e) {
					messageArea.appendText("No such element exception, not enough information provided so account not created.\n");
				}
				
				scanner.close();
				
			}
			
			reader.close();
			messageArea.appendText("\nFile imported.\n");
		} catch (NullPointerException e) {
			messageArea.appendText("File not selected, unable to import transactions to file.\n");
		}
			
			
	}

    
    /**
     * Opens an account and adds to the AccountDatabase
     * @param event The semantic event that indicates user clicked "Open account" button
     */
    @FXML
    void openAcc(ActionEvent event) {
    	
    	try {
    		
    		// Create instances of profile and date based on text entry boxes 		
        	Profile person = new Profile(fName_OpenClose.getText(), lName_OpenClose.getText());		
        	Date dateOpen = new Date(month.getText() + "/" + day.getText() + "/" + year.getText());
        	double inpBalance = Double.parseDouble(balance.getText());
        	boolean opened = false;
        	
        	//Check for inputed incomplete names
    		if(fName_OpenClose.getText().isEmpty() || lName_OpenClose.getText().isEmpty()) {
    			throw new Exception("Please enter a full name.\n");
    		}
    		
    		//Check for negative inputed values
        	if(inpBalance < 0) {
        		throw new IllegalArgumentException("No negative numbers, please enter a valid balance.\n");
        	}
        	
        	if(dateOpen.isValid()) {
        		
        		String accType = ((RadioButton) tgOpenClose.getSelectedToggle()).getText();
        		
        		//add account according to account type
        		switch(accType) {
        		case "Checking": 
        			
        			boolean directDepInp;
        			if (directDep.isSelected()) {
        				directDepInp = true;
        			}
        			else {
        				directDepInp = false;
        			}
        			
        			Checking checking = new Checking(person, inpBalance, dateOpen, directDepInp);
        			opened = db.add(checking);
        			break;
        			
        		case "Savings":
        			
        			boolean isLoyalInp;
        			if (isLoyal.isSelected()) {
        				isLoyalInp = true;
        			}
        			else {
        				isLoyalInp = false;
        			}
        			
        			Savings savings = new Savings(person, inpBalance, dateOpen, isLoyalInp);
        			opened = db.add(savings);
        			break;
        			
        		case "Money Market":
        			
        			MoneyMarket moneyMarket = new MoneyMarket(person, inpBalance, dateOpen);
        			opened = db.add(moneyMarket);
        			break;
        		}
        		
        		if (opened) {
    				messageArea.appendText("Account opened and added to the database.\n");
    			}
    			else {
    				messageArea.appendText("Account is already in the database.\n");
    			}
        		
        		
        	}
        	else {
        		messageArea.appendText(dateOpen.toString() + " is not a valid date!\n");
        	}
    	}
    	
    	catch (NumberFormatException e){
    		messageArea.appendText("Number format exception, enter a valid starting balance.\n");
    	}
    	catch (ArrayIndexOutOfBoundsException e) {
    		messageArea.appendText("Not enough information provided, account could not be created.\n");
    	}
    	catch (NullPointerException e) {
    		messageArea.appendText("No account type chosen.\n");
    	}
    	catch (IllegalArgumentException e) {
    		messageArea.appendText("No negative numbers, please enter a valid balance.\n");
    	}
    	catch (Exception e) {
    		messageArea.appendText("Please enter a full name.\n");
    	}
    	
    	
    	
    }

    
    /**
     * Prints accounts inside the db
     * @param event The semantic event that indicates user chose "Accounts" option under "Print" tab
     */
    @FXML
    void printAcc(ActionEvent event) {
    	String accInfo = db.printAccounts();
    	messageArea.setText(accInfo);
    }

    /**
     * Prints account statements inside the db by date opened
     * @param event The semantic event that indicates user chose "Statements by Date" under "Print" tab
     */
    @FXML
    void printByDate(ActionEvent event) {
    	String accInfo = db.printByDateOpen();
    	messageArea.setText(accInfo);
    }

    /**
     * Prints account statements inside the db by last name
     * @param event The semantic event that indicates user chose "Statements by Last Name" under "Print" tab
     */
    @FXML
    void printByLastName(ActionEvent event) {
    	String accInfo = db.printByLastName();
    	messageArea.setText(accInfo);
    }
    
    /**
     * Voids specific options in UI if Savings account type is selected
     * @param event The semantic event that indicates user chose Savings
     */
    @FXML
    void selectSavings(ActionEvent event) {
    	directDep.setSelected(false);
    	isLoyal.setSelected(false);
    	directDep.setDisable(true);
    	isLoyal.setDisable(false);
    }

    /**
     * Voids specific options in UI if MoneyMarket account type is selected
     * @param event The semantic event that indicates user chose MoneyMarket
     */
    @FXML
    void selectMoneyMarket(ActionEvent event) {
    	directDep.setSelected(false);
    	isLoyal.setSelected(false);
    	directDep.setDisable(true);
    	isLoyal.setDisable(true);
    }

    /**
     * Voids specific options in UI if Checking account type is selected
     * @param event The semantic event that indicates user chose Checking
     */
    @FXML
    void selectChecking(ActionEvent event) {
    	directDep.setSelected(false);
    	isLoyal.setSelected(false);
    	directDep.setDisable(false);
    	isLoyal.setDisable(true);
    }
    
    /**
     * Withdraws funds from an account in the AccountDatabase
     * @param event The semantic event that indicates a user clicked "Withdraw funds" button
     */
    @FXML
    void withdrawFunds(ActionEvent event) {
    	try {
    		
    		Profile person = new Profile(fName_DepWith.getText(), lName_DepWith.getText());
    		double inpBalance = Double.parseDouble(amount.getText());
    		int withdrawn = 0;
    		
        	if(inpBalance < 0) {
        		throw new IllegalArgumentException("No negative numbers, please enter a valid balance.\n");
        	}
    		
    		String accType = ((RadioButton) tgDepositWithdraw.getSelectedToggle()).getText();
    		
    		//withdraws funds according to specified account type
    		switch(accType) {
    		case "Checking":
    			Account currCheckingAcc = new Checking(person);
    			withdrawn = db.withdrawal(currCheckingAcc, inpBalance);
    			break;
    		case "Savings":
    			Account currSavingsAcc = new Savings(person);
    			withdrawn = db.withdrawal(currSavingsAcc, inpBalance);
    			break;
    		case "Money Market":
    			Account currMoneyMarketAcc = new MoneyMarket(person);
    			withdrawn = db.withdrawal(currMoneyMarketAcc, inpBalance);
    			break;
    		}
    		
    		
    		if (withdrawn == 0) {
    			messageArea.appendText(String.format("%.2f", inpBalance) + " withdrawn from account.\n");
    		}
    		else if (withdrawn == 1) {
    			messageArea.appendText("Insufficient funds.\n");
    		}
    		else {
    			messageArea.appendText("Account does not exist.\n");
    		}
    		
    	}
    	catch(NumberFormatException e) {
    		messageArea.appendText("Number format exception, enter a valid amount to withdraw.\n");
    	}
    	catch(NullPointerException e) {
    		messageArea.appendText("Select a valid account type to withdraw from.\n");
    	}
    	catch (IllegalArgumentException e) {
    		messageArea.appendText("No negative numbers, please enter a valid balance.\n");
    	}
    	
    }
    

}
