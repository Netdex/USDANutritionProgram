package parser.parsables;

import javax.swing.JOptionPane;

import parser.Formattable;
import parser.InvalidParseDataException;

/**
 * A structure storing weight information of this FoodItem
 *
 * @author Gordon Guan
 */
public class WeightUnit implements Parsable<WeightUnit>, Formattable {

	public static final int PARSE_DATA_LENGTH = 7;
	/**
	 * Represents a weight unit of one gram.
	 */
	public static final WeightUnit GRAM = getGram();
	public static final WeightUnit SAMPLE = new WeightUnit();
	private int ndbNo;
	// --Commented out by Inspection (11/20/2015 12:10 PM):private int seq;
	private double amount;
	private String desc;
	private double gramWeight;
	// --Commented out by Inspection (11/20/2015 12:10 PM):private int numDataPts;
	// --Commented out by Inspection (11/20/2015 12:10 PM):private double stdDev;

	public WeightUnit() {

	}

	/**
	 * Gets a unit representing a gram
	 * 
	 * @return a unit representing a gram
	 */
	private static WeightUnit getGram() {
		try {
			return new WeightUnit().parse(new String[] { "-1", "", "1", "gram",
					"1", "", "" });
		} catch (InvalidParseDataException e) {
			JOptionPane.showConfirmDialog(null,
					"Cannot parse 'Gram'\nThis error should never happen",
					"What Did You Do?!", JOptionPane.DEFAULT_OPTION,
					JOptionPane.WARNING_MESSAGE);
		}
		return null;
	}

// --Commented out by Inspection START (11/20/2015 12:10 PM):
//	/**
//	 * @return the parseDataLength
//	 */
//	public static int getParseDataLength() {
//		return PARSE_DATA_LENGTH;
//	}
// --Commented out by Inspection STOP (11/20/2015 12:10 PM)

	@Override
	public WeightUnit parse(String[] data) throws InvalidParseDataException {
		if (data.length != PARSE_DATA_LENGTH)
			throw new InvalidParseDataException();
		ndbNo = Integer.parseInt(data[0]);
		// seq = Integer.parseInt(data[1]);
		amount = Double.parseDouble(data[2]);
		desc = data[3];
		gramWeight = Double.parseDouble(data[4]);
		// numDataPts = data[5].equals("") ? 0 : Integer.parseInt(data[5]);
		// stdDev = data[6].equals("") ? 0 : Double.parseDouble(data[6]);
		return this;
	}

	@Override
	public String getFormat() {
		return Formattable.getFileFormatted(String.format("~%05d~", ndbNo),
				"0", amount + "", "~" + desc + "~", gramWeight + "", "0", "0");

	}

	public int getNDBNo() {
		return ndbNo;
	}

// --Commented out by Inspection START (11/20/2015 12:10 PM):
//	/**
//	 * @return the seq
//	 */
//	public int getSeq() {
//		return seq;
//	}
// --Commented out by Inspection STOP (11/20/2015 12:10 PM)

// --Commented out by Inspection START (11/20/2015 12:10 PM):
//	/**
//	 * @return the amount
//	 */
//	public double getAmount() {
//		return amount;
//	}
// --Commented out by Inspection STOP (11/20/2015 12:10 PM)

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @return the gramWeight
	 */
	public double getGramWeight() {
		return gramWeight;
	}

// --Commented out by Inspection START (11/20/2015 12:10 PM):
//	/**
//	 * @return the numDataPts
//	 */
//	public int getNumDataPts() {
//		return numDataPts;
//	}
// --Commented out by Inspection STOP (11/20/2015 12:10 PM)

// --Commented out by Inspection START (11/20/2015 12:10 PM):
//	/**
//	 * @return the stdDev
//	 */
//	public double getStdDev() {
//		return stdDev;
//	}
// --Commented out by Inspection STOP (11/20/2015 12:10 PM)

	public String toString() {
		return this.getDesc();
	}
}
