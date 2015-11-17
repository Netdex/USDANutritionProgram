package parser.parsables;

import parser.Formattable;
import parser.InvalidParseDataException;

/**
 * A description in-depth of a LanguaL
 *
 * @author Gordon Guan
 */
public class LanguaLDescription implements Parsable<LanguaLDescription>, Formattable {

	public static final int PARSE_DATA_LENGTH = 2;
	private String factorCode;
	private String desc;

	public LanguaLDescription() {

	}

	@Override
	public LanguaLDescription parse(String[] data) throws InvalidParseDataException {
		if (data.length != PARSE_DATA_LENGTH)
			throw new InvalidParseDataException();
		factorCode = data[0];
		desc = data[1];
		return this;

	}

	@Override
	public String getFormat() {
		return Formattable.getFileFormatted("~" + factorCode + "~", "~" + desc + "~");
	}

	/**
	 * Gets the factor code of this langual descriptor
	 *
	 * @return the factor code of this langual descriptor
	 */
	public String getFactorCode() {
		return factorCode;
	}

	/**
	 * Gets the langual's description
	 * @return the langual's description
	 */
	public String getDesc() {
		return desc;
	}

	public String toString() {
		return desc;
	}

}
