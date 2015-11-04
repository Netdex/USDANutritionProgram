package parser;

import java.io.File;

import util.BTreeVisualizer;
import util.HashTable;

public class MainTest {

	/**
	 * THIS CLASS DOES NOTHING BUT TEST MY CODE KTHXBYE
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		parserTest();
	}

	public static void hashTest() {
		HashTable<Integer, String> ht = new HashTable<>();

		BTreeVisualizer<HashTable.HashTableNode<Integer, String>> btv = new BTreeVisualizer<HashTable.HashTableNode<Integer, String>>(
				ht.tree);
		btv.pack();
		btv.setVisible(true);

		for (int i = 0; i < 100; i++) {
			ht.put(i, "TEST" + i);
		}
		System.out.println(ht.get(10));
	}

	public static void parserTest() {
		Parser p = new Parser(new File("USDAFiles/FOOD_DES.TXT"), new File(
				"USDAFiles/NUT_DATA.TXT"), new File("USDAFiles/NUTR_DEF.TXT"),
				new File("USDAFiles/FD_GROUP.TXT"), new File(
						"USDAFiles/WEIGHT.TXT"));

		p.parseData();
		System.out.println();
	}
}
