package parser;

/**
 * Represents an item which can be serialized into a file
 *
 * @author Gordon Guan
 */
public interface Formattable {

	/**
	 * Turn a string array into a properly formatted, delimited string to write into a file
	 *
	 * @param str The string array
	 * @return formatted string to write into a file
	 */
	static String getFileFormatted(String... str) {
		String form = "";
		for(int i = 0; i < str.length - 1; i++){
			form += str[i] + "^";
		}
		form += str[str.length - 1];
		return form;
	}

	String getFormat();
}
