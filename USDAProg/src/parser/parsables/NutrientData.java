package parser.parsables;

import util.DoublyLinkedList;

/**
 * This class contains a list of nutrients which this food item has
 * 
 * @author Netdex
 *
 */

public class NutrientData {
	private DoublyLinkedList<Nutrient> nutrients = new DoublyLinkedList<Nutrient>();

	public NutrientData() {

	}

	public void addNutrient(Nutrient nutr) {
		nutrients.add(nutr);
	}

	public DoublyLinkedList<Nutrient> getNutrients() {
		return nutrients;
	}
	
	public String toString(){
		return nutrients.toString();
	}
}