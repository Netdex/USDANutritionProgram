package parser.parsables;

import parser.InvalidParseDataException;

public interface Parsable<E> {

	public void parse(String[] data) throws InvalidParseDataException;
}
