package parser;

public interface Parsable<E> {

	public void parse(String[] data) throws InvalidParseDataException;
}
