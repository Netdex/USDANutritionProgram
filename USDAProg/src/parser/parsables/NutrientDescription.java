package parser.parsables;

import parser.InvalidParseDataException;

public class NutrientDescription implements Parsable<NutrientDescription> {

	private static final int PARSE_DATA_LENGTH = 6;
	
	/**
	 * Nutrient Number
	 */
	private int nutrNo;
	/**
	 * Unit measured in (grams, mg etc.)
	 */
	private String unit;
	/**
	 * Standard name for this nutrient by INFOODS
	 */
	private String tagName;
	/**
	 * Descriptoin of nutrient
	 */
	private String nutrDesc;
	/**
	 * Number of decimals rounded
	 */
	private int numDec;
	/**
	 * Some weird number
	 */
	private int srOrder;
	
	@Override
	public void parse(String[] data) throws InvalidParseDataException {
		if(data.length != PARSE_DATA_LENGTH)
			throw new InvalidParseDataException();
		nutrNo = Integer.parseInt(data[0]);
		unit = data[1];
		tagName = data[2];
		nutrDesc = data[3];
		numDec = Integer.parseInt(data[4]);
		srOrder = Integer.parseInt(data[5]);
	}

	public NutrientDescription(){
		
	}

	/**
	 * @return the parseDataLength
	 */
	public static int getParseDataLength() {
		return PARSE_DATA_LENGTH;
	}

	/**
	 * @return the nutrNo
	 */
	public int getNutrientNumber() {
		return nutrNo;
	}

	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * @return the tagName
	 */
	public String getTagName() {
		return tagName;
	}

	/**
	 * @return the nutrDesc
	 */
	public String getNutrientDescription() {
		return nutrDesc;
	}

	/**
	 * @return the numDec
	 */
	public int getNumberOfDecimals() {
		return numDec;
	}

	/**
	 * @return the srOrder
	 */
	public int getSrOrder() {
		return srOrder;
	}
	
	public String toString(){
		return this.getNutrientDescription();
	}
}
