package parser;

import java.awt.Image;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

import javax.imageio.ImageIO;

import parser.parsables.FoodItem;

public class ParsableTest {

	/**
	 * THIS CLASS DOES NOTHING BUT TEST MY CODE KTHXBYE
	 * 
	 * @param args
	 * @throws InvalidParseDataException 
	 */
	public static void main(String[] args) throws Exception {
		URL url = new URL("http://authoritynutrition.com/wp-content/uploads/2013/08/bottles-of-vegetable-oil.jpg");
		URLConnection conn = url.openConnection();
		conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1");
		Image img = ImageIO.read(conn.getInputStream());
		System.out.println(img);
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
