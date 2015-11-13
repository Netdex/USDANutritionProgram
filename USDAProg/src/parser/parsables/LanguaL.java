package parser.parsables;

import parser.Formattable;
import parser.InvalidParseDataException;

public class LanguaL implements Parsable<LanguaL>, Formattable {

	public static final int PARSE_DATA_LENGTH = 2;

	private LanguaLDescription langualDescription;

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

	private int NDBNo;
	private String factorCode;

	public LanguaLDescription getLangualDescription() {
		return langualDescription;
	}

	public void setLangualDescription(LanguaLDescription langualDescription) {
		this.langualDescription = langualDescription;
	}

	public LanguaL() {

	}

	public String toString() {
		return langualDescription.getDesc();
	}

	public int getNDBNo() {
		return NDBNo;
	}

	public String getFactorCode() {
		return factorCode;
	}

}
