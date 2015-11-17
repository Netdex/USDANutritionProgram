package parser.parsables;

import parser.util.DoublyLinkedList;

/**
 * Contains all the possible weights of a food item
 * @author Gordon Guan
 */
public class FoodWeight {
    private final DoublyLinkedList<WeightUnit> units = new DoublyLinkedList<>();
    public FoodWeight(){

    }

    /**
     * Adds a weight unit to this food's weight descriptor
     *
     * @param wu The weight unit to add
     */
    public void addWeightUnit(WeightUnit wu){
        units.add(wu);
    }

    /**
     * Gets a list of possible weight units
     *
     * @return a list of possible weight units
     */
    public WeightUnit[] getWeightUnits(){
        return units.toArray(WeightUnit.SAMPLE);
    }
}
