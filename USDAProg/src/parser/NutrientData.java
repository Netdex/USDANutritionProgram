package parser;

import java.util.ArrayList;


/**
 * This class contains a list of nutrients which this food item has
 * @author Netdex
 *
 */

public class NutrientData {
	private ArrayList<Nutrient> nutrients = new ArrayList<Nutrient>();
	
	public NutrientData(){
		
	}
	
	public void addNutrient(Nutrient nutr){
		nutrients.add(nutr);
	}
	
	public Nutrient[] getNutrients(){
		return nutrients.toArray(new Nutrient[1]);
	}
}
