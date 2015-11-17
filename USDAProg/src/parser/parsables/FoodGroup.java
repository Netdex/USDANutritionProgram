package parser.parsables;

import parser.Formattable;
import parser.InvalidParseDataException;
import parser.util.DoublyLinkedList;

/**
 * Represents a food group
 * 
 * @author Gordon Guan
 *
 */
public class FoodGroup implements Parsable<FoodGroup>, Formattable {

	/**
	 * A sample for generics to create arrays from
	 */
	public static final FoodGroup SAMPLE = new FoodGroup();
	/**
	 * The required length of parse data
	 */
	private static final int PARSE_DATA_LENGTH = 2;
	/**
	 * A list of all foods in this food group
	 */
	private final DoublyLinkedList<FoodItem> foods = new DoublyLinkedList<>();
	private int foodGroupID;
	private String desc;

	public FoodGroup() {

	}

	@Override
	public FoodGroup parse(String[] data) throws InvalidParseDataException {
		if (data.length != PARSE_DATA_LENGTH)
			throw new InvalidParseDataException();
		foodGroupID = Integer.parseInt(data[0]);
		desc = data[1];

		return this;
	}

	@Override
	public String getFormat() {
		return Formattable.getFileFormatted("~" + String.format("%04d", foodGroupID) + "~", "~"
				+ desc + "~");
	}

	/**
	 * Gets a list of all foods in this food group
	 * @return a list of all the foods in this food group
	 */
	public DoublyLinkedList<FoodItem> getFoods() {
		return foods;
	}

	/**
	 * Adds a food item to this food group
	 * @param fi The food item to add
	 */
	public void addFood(FoodItem fi) {
		this.foods.add(fi);
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

	public String toString() {
		return this.getDescription();
	}

}
