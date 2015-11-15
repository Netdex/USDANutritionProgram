package parser.parsables;

import parser.util.DoublyLinkedList;

/**
 * Contains all the possible weights of a food item
 */
public class FoodWeight {
    private DoublyLinkedList<WeightUnit> units = new DoublyLinkedList<>();
    public FoodWeight(){

    }
    public void addWeightUnit(WeightUnit wu){
        units.add(wu);
    }
    public WeightUnit[] getWeightUnits(){
        return units.toArray(WeightUnit.SAMPLE);
    }
}
