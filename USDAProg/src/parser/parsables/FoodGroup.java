package parser.parsables;

import parser.InvalidParseDataException;

public class FoodGroup implements Parsable<FoodGroup> {

	/**
	 * We shouldn't hard code the food group stuff, we must read it from the
	 * file
	 */
	/**
	 * This class will represent a food group as read in from the file. Each
	 * FoodItem will own a FoodGroup.
	 */

	private static final int PARSE_DATA_LENGTH = 2;
	
	@Override
	public void parse(String[] data) throws InvalidParseDataException {
		if(data.length != PARSE_DATA_LENGTH)
			throw new InvalidParseDataException();
		foodGroupID = Integer.parseInt(data[0]);
		desc = data[1];
	}

	private int foodGroupID;

	private String desc;

	public FoodGroup() {

	}

	/**
	 * @return the foodGroupID
	 */
	public int getFoodGroupID() {
		return foodGroupID;
	}

	/**
	 * @return the desc
	 */
	public String getDescription() {
		return desc;
	}
	
	public String toString(){
		return this.getDescription();
	}

}
