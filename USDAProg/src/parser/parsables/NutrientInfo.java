package parser.parsables;

import parser.InvalidParseDataException;

/**
 * Contains information about a certain nutrient. Each Nutrient has a
 * corresponding NutrientInfo.
 * 
 * @author Gordon Guan
 *
 */
public class NutrientInfo implements Parsable<NutrientInfo> {

	public static final int PARSE_DATA_LENGTH = 6;

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
	 * Description of nutrient
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
	public NutrientInfo parse(String[] data) throws InvalidParseDataException {
		if (data.length != PARSE_DATA_LENGTH)
			throw new InvalidParseDataException();
		nutrNo = Integer.parseInt(data[0]);
		unit = data[1];
		tagName = data[2];
		nutrDesc = data[3];
		numDec = Integer.parseInt(data[4]);
		srOrder = Integer.parseInt(data[5]);
		return this;
	}

	public NutrientInfo() {

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
	public String getNutrientName() {
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

	public String toString() {
		return this.getNutrientName();
	}
}
