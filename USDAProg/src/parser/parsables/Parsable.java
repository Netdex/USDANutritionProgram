package parser.parsables;

import parser.InvalidParseDataException;

/**
 * An interface which represents an object which can be deserialized from a file
 *
 * @param <E> The type of the object after deserialization
 * @author Gordon Guan
 */
interface Parsable<E> {

	/**
	 * Sets the appropriate fields in a Parsable structure from the given data
	 * @param data The data to be parsed
	 * @throws InvalidParseDataException When parse data does not meet specifications required
	 */
	E parse(String[] data) throws InvalidParseDataException;
}
