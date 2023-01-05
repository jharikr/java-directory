package main.core;
/**
 * This class implements the Directory interface using simple arrays. Entries 
 * are stored in an array and manipulated using the implemented methods.
 * 
 * @Author Jharik A Richardson
 * @Date 1st April 2019
 */
class ArrayDirectory implements Directory {
	
	private Entry[] entry;
	private int counter;
	private final int ARRAY_SIZE = 3000;
	
	/**
	 * Instantiates the array and assigns the default size.
	 */
	public ArrayDirectory() {
		this.entry = new Entry [ARRAY_SIZE];
		this.counter = 1; 
	}
	
	private int getCounter() {
		return counter;
	}
	
	/**
	 * Determines if the array is empty
	 */
	private boolean isEmpty() {
		return getCounter() == 1;
	}
	
 	/**
	 * Increases the capacity of the array.
	 */
	private void increaseArraySize() {
		Entry temp[] = entry;
		entry =	new Entry [(int) (temp.length * 1.35)]; 
		
		for(int i = 0; i < temp.length; i++) { 
			entry[i] = temp [i];
		}
	}
	
	/**
 	 * Shifts elements down (left) in an array by one (1) until the specified index. The 
 	 * element at the specified index is ultimately removed.
 	 * 
 	 * @param index the index to shift elements downwards until
 	 */
 	private void shiftElementLeft(int index) {
 		for (int i = index; i < getCounter() - 1; ++i) {
			entry [i] = entry [i + 1];
		}
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
			
			for (int i = 0; i < getCounter() - 1; i++) {
				if (entry[i].getTelExtension() == num) {
					return i;
				}
			}
		}

		for (int i = 0; i < getCounter() - 1; i++) {
			if (entry[i].getSurname().contains(filter)) {
				return i;
			}
		}
		return -1;
	}
	
	@Override
	public void insertEntry(Entry entry){ 
		
		if (getCounter() == this.entry.length) { 
			increaseArraySize();
		}

		String surname = entry.getSurname();
		int i = getCounter() - 1;

		//finds insertion point & shifts elements to the right
		for(; i > 0 && this.entry[i - 1].getSurname().compareTo(surname) > 0; i--) {
			this.entry[i] = this.entry[i-1];
		}
		this.entry[i] = entry;
		counter ++;		
	}
	
	
	@Override
	public void deleteEntry(String filter) throws ElementNotFoundException {
		
		if (isEmpty()) {
			return;
		}
		
		int index = indexOf(filter);
		
		if (index == -1) {
			throw new ElementNotFoundException ("\n Entry Not Found In The Directory");
		}
		
		shiftElementLeft(index);
		counter --;
	}
	
	@Override
	public int lookUpEntry(String name) throws ElementNotFoundException {
		
		if (isEmpty()) {
			return -1;
		}
		
		int index = indexOf(name);
		
		if (index == -1){
			throw new ElementNotFoundException("\n Entry Not Found In The Directory");			
		}
		
		return entry[index].getTelExtension();		
	}
	
	@Override 
	public void changeTelExtension(String name, int newExtNum) throws ElementNotFoundException, InvalidTelephoneExtensionException  { //maybe two parameters: name & number
		
		if (isEmpty()) {
			return;
		}

		int index = indexOf(name);

		if (index == -1) {
			throw new ElementNotFoundException ("\n Entry Not Found In The Directory");
		}
		
		entry[index].setTelExtension(newExtNum);
	}
	
	@Override
	public void printDirectory() {
		for(int i = 0; i < getCounter() - 1; i++) {
			System.out.println( entry[i] );
		}
	}
}