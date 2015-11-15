package parser;

import parser.parsables.FoodItem;
import parser.util.BinaryTreeMap;

import java.io.File;
import java.util.Scanner;

public class ParsableTest {

	/**
	 * THIS CLASS DOES NOTHING BUT TEST MY CODE KTHXBYE
	 * 
	 * @param args
	 * @throws InvalidParseDataException 
	 */
	public static void main(String[] args) throws Exception {
		BinaryTreeMap<Integer, String> btm = new BinaryTreeMap<>();
		btm.put(1, "Teest");
		btm.put(2, "Teest");
		btm.put(0, "Teest");
		btm.put(-1, "teest");
		System.out.println(btm);
		btm.put(0, "NOOO");
		System.out.println(btm);
	}

	public static void dataTest() {
		Scanner sc = new Scanner(System.in);
		DataManager.getInstance().init(new File("USDAFiles/FOOD_DES.TXT"),
				new File("USDAFiles/NUT_DATA.TXT"), new File("USDAFiles/NUTR_DEF.TXT"),
				new File("USDAFiles/FD_GROUP.TXT"), new File("USDAFiles/WEIGHT.TXT"),
				new File("USDAFiles/LANGUAL.txt"), new File("USDAFiles/LANGDESC.TXT"),
				new File("USDAFiles/FOOTNOTE.TXT"));
		for (;;) {
			FoodItem[] items = DataManager.getInstance().searchForItem(sc.nextLine().split(" "));
			for (FoodItem fi : items) {
				System.out.println(fi);
			}
		}

	}

	

	
}
