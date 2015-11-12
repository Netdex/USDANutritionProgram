package parser;

public interface Formattable {

	public String getFormat();
	
	public static String getFileFormatted(String... str){
		String form = "";
		for(int i = 0; i < str.length - 1; i++){
			form += str[i] + "^";
		}
		form += str[str.length - 1];
		return form;
	}
}
