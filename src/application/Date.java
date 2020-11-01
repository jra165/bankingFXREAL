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
	@Override
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
		
		final int minYear = 0;
		final int minDay = 1;
		final int maxDay = 31;
		final int febNoLeapDay = 28;
		final int febLeapDay = 29;
		
		final int JAN = 1;				//January is 1st month
		final int FEB = 2;				//February is 2nd month of year
		final int MAR = 3;				//March is 3rd month of year
		final int APR = 4;				//April is 4th month of year
		final int MAY = 5;				//May is 5th month of year
		final int JUNE = 6;				//June is 6th month of year
		final int JULY = 7;				//July is 7th month of year
		final int AUG = 8;				//August is 8th month of year
		final int SEP = 9;				//September is 9th month of year
		final int OCT = 10;				//October is 10th month of year
		final int NOV = 11;				//November is 11th month of year
		final int DEC = 12;				//December is 12th month of year	
		
		
		if ((year < minYear) || (month < JAN) || (month > DEC) || (day < minDay) || (day > maxDay)) {
			return false;
		}
		
		switch (month) {
		case JAN: return true;
		case FEB: return (isLeap(year) ? day <= febLeapDay : day <= febNoLeapDay);
		case MAR: return true;
		case APR: return day < maxDay;
		case MAY: return true;
		case JUNE: return day < maxDay;
		case JULY: return true;
		case AUG: return true;
		case SEP: return day < maxDay;
		case OCT: return true;
		case NOV: return day < maxDay;
		default: return true;
		
		}
		
	}
	
	/**
	 * Checks if a year is a leap year
	 * @param year The year being evaluated for leap qualifications
	 * @return true if leap year, false otherwise
	 */
	private boolean isLeap(int year) {
		
		final int leapMult = 4;
		final int leapMultCentury = 400;
		final int leapNonMultCentury = 100;
		
		
		if (year % leapMult != 0) {
			return false;
		}
		else if (year % leapMultCentury == 0) {
			return true;
		}
		else if (year % leapNonMultCentury == 0) {
			return false;
		}
		else {
			return true;
		}
	}
	


}
