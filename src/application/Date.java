package application;
/**
The Date class contains the properties and methods associated with the Date object.
Properties include year, month, day
Methods include getYear, getMonth, getDay, compareTo, toString, isValid, isLeap
@author Joshua Atienza, Kyle Lee
*/
public class Date implements Comparable<Date> {

	private int year;
	private int month;
	private int day;
	
	/**
	 * Creates a Date with the specified month, day, year
	 * @param date The String version of the date
	 */
	public Date(String date) {
		String[] dateParsed = date.split("/");
		this.month = Integer.parseInt(dateParsed[0]);
		this.day = Integer.parseInt(dateParsed[1]);
		this.year = Integer.parseInt(dateParsed[2]);
	}
	
	/**
	 * Gets the year of the date
	 * @return year The year associated with the date
	 */
	public int getYear() {
		return year;
	}
	
	/**
	 * Gets the month of the date
	 * @return month The month associated with the date
	 */
	public int getMonth() {  
		return month;
	}
	
	/**
	 * Gets the day of the date
	 * @return day The day associated with the date
	 */
	public int getDay() {
		return day;
	}
	
	/**
	 * Compares a Date with another Date
	 * Compares the year, month, and date to determine which comes first
	 * @param o The Date being compared to
	 * @return -1 if date comes before date in parameter, 0 if dates equal, 1 if date comes after date in parameter
	 */
	@Override
	public int compareTo(Date o) {
		if (year < o.getYear()) {
			return -1;
		}
		else if (year > o.getYear()) {
			return 1;
		}
		else {
			if (month < o.getMonth()) {
				return -1;
			}
			else if (month > o.getMonth()) {
				return 1;
			}
			else { 
				if (day < o.getDay()) {
					return -1;
				}
				else if (day > o.getDay()) {
					return 1;
				}
				else {
					return 0;
				}
			}
			
		}
		
	}
	
	/**
	 * Converts the Date to a String representation
	 * Date follows the format mm/dd/yyyy
	 * @return dateStr The String representation of the Date
	 */
	public String toString() {
		String dateStr = Integer.toString(month) + "/" + Integer.toString(day) + "/" + Integer.toString(year);
		return dateStr;
	}
	
	/** 
	 * Checks if a given date is valid
	 * Checks for number of days in a month, leap years, etc.
	 * @return true if date valid, false otherwise
	 */
	public boolean isValid() { 
		
		if ((year < 0) || (month < 1) || (month > 12) || (day < 1) || (day > 31)) {
			return false;
		}
		
		switch (month) {
		case 1: return true;
		case 2: return (isLeap(year) ? day <= 29 : day <= 28);
		case 3: return true;
		case 4: return day < 31;
		case 5: return true;
		case 6: return day < 31;
		case 7: return true;
		case 8: return true;
		case 9: return day < 31;
		case 10: return true;
		case 11: return day < 31;
		default: return true;
		
		}
		
	}
	
	/**
	 * Checks if a year is a leap year
	 * @param year The year being evaluated for leap qualifications
	 * @return true if leap year, false otherwise
	 */
	private boolean isLeap(int year) {
		if (year % 4 != 0) {
			return false;
		}
		else if (year % 400 == 0) {
			return true;
		}
		else if (year % 100 == 0) {
			return false;
		}
		else {
			return true;
		}
	}
	


}
