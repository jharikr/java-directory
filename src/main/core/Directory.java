package main.core;

/**
 * An interface that allows you manipulate entries in a directory using the Entry 
 * Class. The interface supports the insertions and deletion of entries, the 
 * retrieval and editing of a telephone extension, & printing of the directory's 
 * content.
 * 
 * @Author Jharik A Richardson
 * @Date 4th April 2019
 */
public interface Directory {
	
	/**
	 * Actively inserts a new entry into the directory in alphabetical order.
	 * 
	 * @param entry the new entry 
	 */
	public void insertEntry(Entry entry);
	
	/**
	 * Deletes an entry in the directory by using either a surname or a telephone 
	 * extension as a search criteria.
	 * 
	 * @param filter the surname or a telephone extension
	 * @throws ElementNotFoundException if entry is not located in the directory
	 */
	public void deleteEntry(String filter) throws ElementNotFoundException;
	
	/**
	 * Finds an extension number of entry in the directory using the entry's surname
	 * as a search criteria. 
	 * 
	 * @param name the surname 
	 * @return	the corresponding extension number 
	 * @throws ElementNotFoundException if entry is not located in the directory
	 */
	public int lookUpEntry(String name) throws ElementNotFoundException;
	
	/**
	 * Changes an entry's telephone extension using the entry's surname as a search 
	 * criteria.
	 * 
	 * @param name the surname
	 * @param newNum the new telephone extension 
	 * @throws ElementNotFoundException if entry is not located in the directory
	 * @throws InvalidTelephoneExtensionException if the telephone extension is not valid
	 */
	public void changeTelExtension(String name, int newExtNum) throws InvalidTelephoneExtensionException, ElementNotFoundException;
	
	/*
	 * Prints out the contents of the directory onto the console.
	 */
	public void printDirectory ();

}
