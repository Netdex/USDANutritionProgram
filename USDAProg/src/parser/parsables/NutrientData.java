
package parser.parsables;

import parser.Formattable;
import parser.util.DoublyLinkedList;

/**
 * This class contains a list of nutrients which this food item has
 * 
 * @author Gordon Guan
 *
 */

public class NutrientData implements Formattable {
	private DoublyLinkedList<Nutrient> nutrients = new DoublyLinkedList<Nutrient>();

	public NutrientData() {

	}

	public void addNutrient(Nutrient nutr) {
		nutrients.add(nutr);
	}

	public DoublyLinkedList<Nutrient> getNutrients() {
		return nutrients;
	}

	public String toString() {
		return nutrients.toString();
	}

	@Override
	public String getFormat() {
		// TODO Auto-generated method stub
		return null;
	}
}