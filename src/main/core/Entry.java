package main.core;

/**
 * Stores information about a single entry in a telephone directory 
 * 
 * @Author Jharik A Richardson
 * @Date 28th April 2019
 */
public class Entry {
	
	private String surname;
	private String initials;
	private int telExtension;
	
	/**
	 * Creates an entry to that contains information to be stored into a directory
	 *
	 * @param surname the surname of to be stored in the entry 
	 * @param initials the initials of to be stored in the entry 
	 * @param telExtension the telephone extension number of to be stored in the entry 
	 * @throws ElementNotFoundException if telExtension is not of length 5
	 */
	public Entry (String surname, String initials, int telExtension) throws InvalidTelephoneExtensionException {
		setSurname(surname);
		setInitials(initials);
		setTelExtension(telExtension);
	}
	
	public String getSurname() {
		return surname;
	}
	
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public String getInitials() {
		return initials;
	}
	
	public void setInitials(String initials) {
		this.initials = initials;
	}
	
	public int getTelExtension() { 
		return telExtension;
	}
	
	public void setTelExtension(int telExtension) throws InvalidTelephoneExtensionException { 		
		
		if (Integer.toString(telExtension).length() != 5) {
			throw new InvalidTelephoneExtensionException("\n Error: Invalid telephone extension - must be five digits in length ");
		}
		
		else {
			this.telExtension =  telExtension;
		}
	}	
	
	@Override
	public String toString() {
		return String.format("%13s%10s%10d",getSurname(), getInitials(), getTelExtension());
	}
}