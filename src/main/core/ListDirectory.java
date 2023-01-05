package main.core;

import java.util.List;
import java.util.LinkedList;

/**
 * This class implements the Directory interface by using the the Java Collections 
 * List interface and LinkedList class. Each entry is stored and manipulated on a 
 * single linked list.
 * 
 * @Author Jharik A Richardson
 * @Date 10th April 2019
 */
public class ListDirectory implements Directory {
	
	private List <Entry> entry;
	
	/**
	 * Default constructor that instantiates the LinkedList 
	 */
	public ListDirectory() {
		entry = new LinkedList <Entry>();
	}
	
	/**
	 * Finds the index (location) of an entry in the directory by using either a surname
	 * or telephone extension as at search criteria. If criteria exists in the directory,
	 * the index of the entry is returned, else -1 is returned (does not exist in the 
	 * directory.
	 * 
	 * @param filter the name or number used to find index of the entry
	 * @return the index of the entry
	 */
	private int indexOf(String filter) {
		
		if (Numeric.isNumeric(filter)) {
			
			int num = Integer.parseInt(filter);
			
			for (int i = 0; i < entry.size(); i++) {
				if (entry.get(i).getTelExtension() == num) {
					return i;
				}
			}
		}

		for (int i = 0; i < entry.size(); i++) {
			if (entry.get(i).getSurname().contains(filter)) {
				return i;
			}
		}
		return - 1;
	}
	
	@Override
	public void insertEntry(Entry entry) {
		
		String surname = entry.getSurname();
		int i = this.entry.size();
		
		//determines the point insertion on the linked list
		for(; i > 0 && this.entry.get(i - 1).getSurname().compareTo(surname) > 0; i--) {
			
		}
		this.entry.add(i, entry);
	}
	
	@Override
	public void deleteEntry(String filter) throws ElementNotFoundException { 
		
		if (entry.isEmpty()) {
			return;
		}
				
		int index = indexOf(filter);

		if (index == -1) {
			throw new ElementNotFoundException ("\nEntry Not Found In The Directory");
		}
		
		entry.remove(index);
	}

	@Override
	public int lookUpEntry(String name) throws ElementNotFoundException { 
		
		if (entry.isEmpty()) {
			return -1;
		}
		
		int index = indexOf(name);

		if (index == -1) {
			throw new ElementNotFoundException ("\nEntry Not Found In The Directory");
		}
		
		return entry.get(index).getTelExtension();
	}

	@Override
	public void changeTelExtension(String name, int newExtNum) throws ElementNotFoundException, InvalidTelephoneExtensionException {

		if (entry.isEmpty()) {
			return;
		}

		int index = indexOf(name);

		if (index == -1) {
			throw new ElementNotFoundException ("\nEntry Not Found In The Directory");
		}
		
		entry.get(index).setTelExtension(newExtNum);
	}

	@Override
	public void printDirectory() {
		entry.forEach(System.out::println);
	}
	
	
	@Override
	public String toString() {	
		
		StringBuilder directory = new StringBuilder();
		
		for (Entry e: entry ) {
			directory.append(e);
		}
		
	return entry.toString().replace("[", "").replace("]", "").replace(",", "");
	}
}