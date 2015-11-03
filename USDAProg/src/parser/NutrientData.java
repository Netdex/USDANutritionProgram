package parser;

import java.util.ArrayList;

import java.util.ArrayList;

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
