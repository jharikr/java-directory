package main.core;

class Numeric {

	/**
	 * Determines if a given input is a string or integer. If the value is a number, true is 
	 * returned, else the value is string and returns false.
	 * 
	 * @param filter a string or an integer value to test 
	 * @return whether if the @param is a string or integer value
	 */
	 static boolean isNumeric(String filter) {
		
		try {
			int num = Integer.parseInt(filter);
		}
		
		catch (NumberFormatException | NullPointerException message) {
	        return false;
		}
		return true;
	}
}