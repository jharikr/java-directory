package main.core;

import java.util.List;
import java.util.LinkedList;

/**
 * This class implements the Directory interface by using an array of linked list 
 * to create a hash table. The entries are stored in the table using hashing and then 
 * stored on a linked link list based on the hash value.
 *  
 * @Author Jharik A Richardson
 * @Date 14th April 2019
 */
class HashDirectory implements Directory{
	
	private List <Entry> [] entry;
	private final int HASHTABLE_SIZE = 26;
	
	/**
	 * Creates a hash table with a series of linked list. A linked is instantiated at each 
	 * index of the array.
	 */
	public HashDirectory() {
		this.entry = new LinkedList[HASHTABLE_SIZE];
		
		for (int i = 0; i < HASHTABLE_SIZE; i++) {
           entry[i] = new LinkedList();
		}
	}
	
	/**
	 * The hash function calculates the index in which the element should be located in the
	 * hashtable (array). The hash value is the ASCII value of the first char found in the 
	 * surname of an entry, appended to the size of the hashtable.
	 * 
	 * @param key the surname found in an entry
	 * @return the hash value to determine the index in the array
	 */
	private int hashFunction(String key) {	
		return Character.getNumericValue(key.charAt(0)) % HASHTABLE_SIZE;
	}
	

	/**
	 * Finds the index (location) of an entry in the directory by using a surname
	 * as at search criteria. If criteria exists in the directory, the index of 
	 * the entry is returned, else -1 is returned (does not exist in the directory.
	 * 
	 * @param filter the surnname used to find index of the entry on the
	 * @param hash the index where t
	 * @return the index of the entry
	 */
	private int indexOfSurname(String filter, int hash) {
			
		for (int i = 0; i < entry[hash].size(); i++) {
			if (entry[hash].get(i).getSurname().contains(filter)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Deletes a surname from the directory if found.
	 * 
	 * @param filter the string to delete
	 */
	private void deleteBySurname(String filter) throws ElementNotFoundException {
		
		int hash = hashFunction(filter);

		if (entry[hash].isEmpty()) {
			return;
		}

		int index = indexOfSurname(filter,hash);

		if (index == -1) {
			throw new ElementNotFoundException ("\nEntry Not Found In The Directory");
		}
		entry[hash].remove(index);
	}
	
	/**
	 * Deletes a telephone extension from the directory 
	 * 
	 * @param filter the telephone extension
	 * @return if successful
	 */
	public boolean hasDeletedNum(String filter) {
		
		int num = Integer.parseInt(filter);
		
		for (int i = 0; i < HASHTABLE_SIZE; i++) {
			for (int x = 0; x < entry[i].size(); x ++) {
				if (entry[i].get(x).getTelExtension() == num ) {
					return entry[i].remove(x) != null;
				}
			}
		}
		return false;
	}
	
	
	@Override
	public void insertEntry(Entry entry) {
		
		String surname = entry.getSurname();
		int hash = hashFunction(surname);
		int i = this.entry[hash].size();
		
		//determines the index of the entry on the linked list
		for(; i > 0 && this.entry[hash].get(i - 1).getSurname().compareTo(surname) > 0; i--) {
			
		}
		
		this.entry[hash].add(i, entry);
	}

	@Override
	public void deleteEntry(String filter) throws ElementNotFoundException {
		
		if (Numeric.isNumeric(filter)) {
			if (!hasDeletedNum(filter)) {
				throw new ElementNotFoundException ("\nEntry Not Found In The Directory");
			
			}
		}
		deleteBySurname(filter);
	}

	@Override
	public int lookUpEntry(String name) throws ElementNotFoundException {
		
		int hash = hashFunction(name);
		
		if (entry[hash].isEmpty()) {
			return -1;
		}
		
		int index = indexOfSurname(name, hash);

		if (index == -1) {
			throw new ElementNotFoundException ("\nEntry Not Found In The Directory");
		}
		
		return entry[hash].get(index).getTelExtension();
	}

	@Override
	public void changeTelExtension(String name, int newNum) throws InvalidTelephoneExtensionException, ElementNotFoundException {
		
		int hash = hashFunction(name);

		if (entry[hash].isEmpty()) {
			return;
		}

		int index = indexOfSurname(name, hash);

		if (index == -1) {
			throw new ElementNotFoundException ("\nEntry Not Found In The Directory");
		}

		entry[hash].get(index).setTelExtension(newNum);
	}
	
	@Override
	public void printDirectory() {
		for (int i = 0; i < HASHTABLE_SIZE; i++) {
			entry[i].forEach(System.out::println);
		}
	}
}
