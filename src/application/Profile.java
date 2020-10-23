package application;
/**
 * The Profile class contains the properties and methods associated with the Profile object.
 * Properties include first name and last name
 * Methods include get_fname, get_lname, equals
 * @author Joshua Atienza, Kyle Lee
 *
 */
public class Profile {
	
	private String fname;
	private String lname;

	/**
	 * Creates a Profile object with the specified first and last names
	 * @param fname The first name of the account holder
	 * @param lname The last name of the account holder
	 */
	public Profile(String fname, String lname) {
		this.fname = fname;
		this.lname = lname;
	}
	
	/**
	 * Gets the first name of the account holder
	 * @return fname The first name of the account holder
	 */
	public String get_fname() {
		return fname;
	}
	
	/**
	 * Gets the last name of the account holder
	 * @return lname The last name of the account holder
	 */
	public String get_lname() {
		return lname;
	}
	
	/**
	 * Checks if Profile is equivalent to obj being compared to
	 * Checks if obj is instanceof Profile and if all data fields are equivalent
	 * @param obj The object being compared to a particular Profile
	 * @return true if Profile is equivalent to object, false otherwise
	 */
	@Override
	public boolean equals(Object obj){
		if (obj == this) {
			return true;
		}
		
		if (obj instanceof Profile) {
			Profile person = (Profile) obj;	
			return fname.equals(person.get_fname()) && lname.equals(person.get_lname()); 	 
		}
		
		return false;
	}
	
}
