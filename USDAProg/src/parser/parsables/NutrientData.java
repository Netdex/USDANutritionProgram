
package parser.parsables;

import parser.util.DoublyLinkedList;

/**
 * This class contains a list of nutrients which a food item has
 * 
 * @author Gordon Guan
 *
 */

public class NutrientData {
	private final DoublyLinkedList<Nutrient> nutrients = new DoublyLinkedList<>();
	
	public NutrientData() {

	}

	/**
	 * Add a nutrient to the data
	 *
	 * @param nutr the nutrient to add
	 */
	public void addNutrient(Nutrient nutr) {
		nutrients.add(nutr);
	}

	/**
	 * Get the nutrients this data has
	 * @return the nutrients this data has
	 */
	public DoublyLinkedList<Nutrient> getNutrients() {
		return nutrients;
	}

// --Commented out by Inspection START (11/20/2015 12:10 PM):
//	/**
//	 * Gets the nutrients this data has as an array
//	 * @return the nutrients this data has
//	 */
//	public Nutrient[] getNutrientArray() {
//		return nutrients.toArray(nutrients.getFirst());
//	}
// --Commented out by Inspection STOP (11/20/2015 12:10 PM)

	public String toString() {
		return nutrients.toString();
	}

	
}