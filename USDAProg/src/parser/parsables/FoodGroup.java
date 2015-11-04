package parser.parsables;

import parser.InvalidParseDataException;

/**
 * Represents a food group
 * @author Netdex
 *
 */
public class FoodGroup implements Parsable<FoodGroup> {
	
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
	 * @return The ID of this food group
	 */
	public int getFoodGroupID() {
		return foodGroupID;
	}

	/**
	 * @return The description of this food group
	 */
	public String getDescription() {
		return desc;
	}
	
	public String toString(){
		return this.getDescription();
	}

}
