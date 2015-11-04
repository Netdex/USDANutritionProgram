package parser.parsables;

import parser.InvalidParseDataException;

public interface Parsable<E> {

	/**
	 * Sets the appropriate fields in a Parsable structure from the given data
	 * @param data The data to be parsed
	 * @throws InvalidParseDataException When parse data does not meet specifications required
	 */
	public void parse(String[] data) throws InvalidParseDataException;
}
