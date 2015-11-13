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

	public static FoodGroup SAMPLE = new FoodGroup();

	public static final int PARSE_DATA_LENGTH = 2;
	private DoublyLinkedList<FoodItem> foods = new DoublyLinkedList<FoodItem>();

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

	private int foodGroupID;

	private String desc;

	public FoodGroup() {

	}

	public DoublyLinkedList<FoodItem> getFoods() {
		return foods;
	}

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
