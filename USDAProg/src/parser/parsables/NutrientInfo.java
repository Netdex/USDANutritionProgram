package parser.parsables;

import parser.Formattable;
import parser.InvalidParseDataException;

/**
 * Contains information about a certain nutrient. Each Nutrient has a
 * corresponding NutrientInfo.
 * 
 * @author Gordon Guan
 *
 */
public class NutrientInfo implements Parsable<NutrientInfo>, Formattable {

	public static final NutrientInfo SAMPLE = new NutrientInfo();
	public static final int PARSE_DATA_LENGTH = 6;
	
	@Override
	public NutrientInfo parse(String[] data) throws InvalidParseDataException {
		if (data.length != PARSE_DATA_LENGTH)
			throw new InvalidParseDataException();
		nutrNo = Integer.parseInt(data[0]);
		unit = data[1];
		tagName = data[2];
		nutrDesc = data[3];
		numDec = Integer.parseInt(data[4]);
//		srOrder = Integer.parseInt(data[5]);
		return this;
	}
	
	@Override
	public String getFormat() {
		return Formattable.getFileFormatted(
				"~" + String.format("%03d", nutrNo) + "~",
				"~" + unit + "~",
				"~" + tagName + "~",
				"~" + nutrDesc + "~",
				numDec + "",
				"~~"
				);
				
	}
	
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
// --Commented out by Inspection START (11/20/2015 12:10 PM):
//	/**
//	 * Some weird number
//	 */
//	private int srOrder;
// --Commented out by Inspection STOP (11/20/2015 12:10 PM)

	
	public NutrientInfo() {

	}

// --Commented out by Inspection START (11/20/2015 12:10 PM):
//	/**
//	 * @return the parseDataLength
//	 */
//	public static int getParseDataLength() {
//		return PARSE_DATA_LENGTH;
//	}
// --Commented out by Inspection STOP (11/20/2015 12:10 PM)

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

// --Commented out by Inspection START (11/20/2015 12:10 PM):
//	/**
//	 * @return the tagName
//	 */
//	public String getTagName() {
//		return tagName;
//	}
// --Commented out by Inspection STOP (11/20/2015 12:10 PM)

	/**
	 * @return the nutrDesc
	 */
	public String getNutrientName() {
		return nutrDesc;
	}

// --Commented out by Inspection START (11/20/2015 12:10 PM):
//	/**
//	 * @return the numDec
//	 */
//	public int getNumberOfDecimals() {
//		return numDec;
//	}
// --Commented out by Inspection STOP (11/20/2015 12:10 PM)

// --Commented out by Inspection START (11/20/2015 12:10 PM):
//	/**
//	 * @return the srOrder
//	 */
//	public int getSrOrder() {
//		return srOrder;
//	}
// --Commented out by Inspection STOP (11/20/2015 12:10 PM)

	public String toString() {
		return this.getNutrientName();
	}

	
}
