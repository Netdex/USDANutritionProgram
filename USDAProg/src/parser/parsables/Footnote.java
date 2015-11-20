package parser.parsables;

import parser.Formattable;
import parser.InvalidParseDataException;

/**
 * Represents a footnote of a food item
 *
 * @author Gordon Guan
 */
public class Footnote implements Parsable<Footnote>, Formattable{

	public static final int PARSE_DATA_LENGTH = 5;
	private int ndbNo;
	private int seqNo;
	private Footnote.FootnoteType footntType;
	private int nutrNo;
	private String footnoteTxt;

	@Override
	public String getFormat() {
		return Formattable.getFileFormatted(
				String.format("~%05d~", ndbNo),
				String.format("~%02d~", seqNo),
				"~~",
				"~~",
				footnoteTxt
				);
	}

	@Override
	public Footnote parse(String[] data) throws InvalidParseDataException {
		if(data.length != PARSE_DATA_LENGTH)
			throw new InvalidParseDataException();
		ndbNo = Integer.parseInt(data[0]);
		seqNo = Integer.parseInt(data[1]);
		footntType = data[2].equals("D")
				? FootnoteType.FOOD_DESCRIPTION : data[2].equals("M")
				? FootnoteType.MEASURE_DESCRIPTION : FootnoteType.NUTRIENT_VALUE;
		nutrNo = data[3].equals("") ? 0 : Integer.parseInt(data[3]);
		footnoteTxt = data[4];
		return this;
	}
	
	/**
	 * @return The nutrient databank number
	 */
	public int getNdbNo() {
		return ndbNo;
	}

// --Commented out by Inspection START (11/20/2015 12:10 PM):
//	/**
//	 * @return The sequence number
//	 */
//	public int getSequenceNo() {
//		return seqNo;
//	}
// --Commented out by Inspection STOP (11/20/2015 12:10 PM)

// --Commented out by Inspection START (11/20/2015 12:10 PM):
//	/**
//	 * @return The footnote type
//	 */
//	public Footnote.FootnoteType getFootnoteType() {
//		return footntType;
//	}
// --Commented out by Inspection STOP (11/20/2015 12:10 PM)

// --Commented out by Inspection START (11/20/2015 12:10 PM):
//	/**
//	 * @return The nutrient number
//	 */
//	public int getNutrNo() {
//		return nutrNo;
//	}
// --Commented out by Inspection STOP (11/20/2015 12:10 PM)

	/**
	 * @return The footnote text
	 */
	public String getFootnoteText() {
		return footnoteTxt;
	}

	/**
	 * Represents one of 3 types a footnote can be
	 *
	 * @author Gordon Guan
	 */
	enum FootnoteType {
		FOOD_DESCRIPTION,
		MEASURE_DESCRIPTION,
		NUTRIENT_VALUE
	}
}
