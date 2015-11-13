package parser;

import java.io.File;
import java.util.Scanner;

import parser.parsables.FoodItem;
import parser.parsables.FoodWeight;

public class ParsableTest {

	/**
	 * THIS CLASS DOES NOTHING BUT TEST MY CODE KTHXBYE
	 * 
	 * @param args
	 * @throws InvalidParseDataException 
	 */
	public static void main(String[] args) throws Exception {
		FoodItem fi = new FoodItem().parse("01001^0100^CANCER WITH SALT^BUTTER,WITH SALT^^^Y^^0^^6.38^4.27^8.79^3.87".split("\\^", -1));
		fi.setWeightInfo(new FoodWeight().parse("01001^1^1^pat (1\" sq, 1/3\" high)^5.0^^".split("\\^", -1)));
		
		DataManager.getInstance().init(new File("USDAFiles/FOOD_DES.TXT"), new File(
				"USDAFiles/NUT_DATA.TXT"), new File("USDAFiles/NUTR_DEF.TXT"),
				new File("USDAFiles/FD_GROUP.TXT"), new File(
						"USDAFiles/WEIGHT.TXT"), new File(
						"USDAFiles/LANGUAL.txt"), new File(
						"USDAFiles/LANGDESC.TXT"), new File(
						"USDAFiles/FOOTNOTE.TXT"));
		DataManager.getInstance().addFoodItem(fi);
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
