package parser.parsables;

import parser.InvalidParseDataException;

public class LanguaLDescription implements Parsable<LanguaLDescription> {

	private static final int PARSE_DATA_LENGTH = 2;
	@Override
	public void parse(String[] data) throws InvalidParseDataException {
		if(data.length != PARSE_DATA_LENGTH)
			throw new InvalidParseDataException();
		factorCode = data[0];
		desc = data[1];
		
	}
	
	public LanguaLDescription(){
		
	}
	
	private String factorCode;
	private String desc;
	
	public String getFactorCode() {
		return factorCode;
	}

	public String getDesc() {
		return desc;
	}
	
	public String toString(){
		return desc;
	}
	
}
