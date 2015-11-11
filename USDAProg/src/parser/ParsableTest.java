package parser;

import java.io.File;
import java.util.Scanner;

import parser.parsables.FoodItem;
import parser.util.BTreeVisualizer;
import parser.util.BinaryTreeMap;

public class ParsableTest {

	/**
	 * THIS CLASS DOES NOTHING BUT TEST MY CODE KTHXBYE
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		dataTest();
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
