package parser;

import java.io.File;

public class MainTest {

	
	/**
	 * THIS CLASS DOES NOTHING BUT TEST MY CODE KTHXBYE
	 * @param args
	 */
	public static void main(String[] args){
		Parser p = new Parser(
				new File("USDAFiles/FOOD_DES.TXT"), 
				new File("USDAFiles/NUT_DATA.TXT"), 
				new File("USDAFiles/NUTR_DEF.TXT"),
				new File("USDAFiles/FD_GROUP.TXT"),
				new File("USDAFiles/WEIGHT.TXT"));
		p.parseData();
		System.out.println();
	}
}
