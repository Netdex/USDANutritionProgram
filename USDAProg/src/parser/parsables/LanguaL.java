package parser.parsables;

import parser.Formattable;
import parser.InvalidParseDataException;

/**
 * Represents a LanguaL descriptor for a food item
 *
 * @author Gordon Guan
 */
public class LanguaL implements Parsable<LanguaL>, Formattable {

	public static final int PARSE_DATA_LENGTH = 2;
	
	public static final LanguaL SAMPLE = new LanguaL();

	private LanguaLDescription langualDescription;
	private int NDBNo;
	private String factorCode;

	public LanguaL() {

	}

	@Override
	public LanguaL parse(String[] data) throws InvalidParseDataException {
		if (data.length != PARSE_DATA_LENGTH)
			throw new InvalidParseDataException();
		NDBNo = Integer.parseInt(data[0]);
		factorCode = data[1];
		return this;
	}

	@Override
	public String getFormat() {
		return Formattable.getFileFormatted(String.format("~%05d~", NDBNo), "~" + factorCode + "~");
	}

	/**
	 * Gets the description of this LanguaL
	 *
	 * @return the description of this LanguaL
	 */
	public LanguaLDescription getLangualDescription() {
		return langualDescription;
	}

	/**
	 * Sets the description of this langual
	 * @param langualDescription the new description
	 */
	public void setLangualDescription(LanguaLDescription langualDescription) {
		this.langualDescription = langualDescription;
	}

	public String toString() {
		return langualDescription.getDesc();
	}

	/**
	 * Gets the NDB reference of this langual
	 * @return the ndb reference
	 */
	public int getNDBNo() {
		return NDBNo;
	}

	/**
	 * Gets the factor lookup code of this langual
	 * @return the factor lookup code of this langual
	 */
	public String getFactorCode() {
		return factorCode;
	}

}
