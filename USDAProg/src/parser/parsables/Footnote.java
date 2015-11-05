package parser.parsables;

import parser.InvalidParseDataException;

public class Footnote implements Parsable<Footnote>{

	private static final int PARSE_DATA_LENGTH = 5;
	
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
	
	private int ndbNo;
	private int seqNo;
	private Footnote.FootnoteType footntType;
	private int nutrNo;
	private String footnoteTxt;
	
	/**
	 * @return The nutrient databank number
	 */
	public int getNdbNo() {
		return ndbNo;
	}

	/**
	 * @return The sequence number
	 */
	public int getSequenceNo() {
		return seqNo;
	}

	/**
	 * @return The footnote type
	 */
	public Footnote.FootnoteType getFootnoteType() {
		return footntType;
	}

	/**
	 * @return The nutrient number
	 */
	public int getNutrNo() {
		return nutrNo;
	}

	/**
	 * @return The footnote text
	 */
	public String getFootnoteText() {
		return footnoteTxt;
	}
	
	enum FootnoteType {
		FOOD_DESCRIPTION,
		MEASURE_DESCRIPTION,
		NUTRIENT_VALUE
	}
}
