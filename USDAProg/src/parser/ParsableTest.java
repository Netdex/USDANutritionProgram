package parser;

import java.io.File;

import parser.parsables.FoodItem;
import parser.util.BTreeVisualizer;
import parser.util.BinaryTreeMap;
import parser.util.DoublyLinkedList;
import parser.util.Selector;

public class ParsableTest {

	/**
	 * THIS CLASS DOES NOTHING BUT TEST MY CODE KTHXBYE
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		dataTest();
	}

	
	public static void dataTest(){
		DataManager.getInstance().init(new File("USDAFiles/FOOD_DES.TXT"), new File("USDAFiles/NUT_DATA.TXT"),
				new File("USDAFiles/NUTR_DEF.TXT"), new File("USDAFiles/FD_GROUP.TXT"),
				new File("USDAFiles/WEIGHT.TXT"), new File("USDAFiles/LANGUAL.txt"), new File("USDAFiles/LANGDESC.TXT"),
				new File("USDAFiles/FOOTNOTE.TXT"));
		FoodItem[] items = DataManager.getInstance().searchForItem(new String[]{"silk", "peach"});
		for(FoodItem fi : items){
			System.out.println(fi);
		}
	}
	public static void hashTest() {
		BinaryTreeMap<Integer, String> ht = new BinaryTreeMap<>();

		BTreeVisualizer<BinaryTreeMap.HashTableNode<Integer, String>> btv = new BTreeVisualizer<BinaryTreeMap.HashTableNode<Integer, String>>(
				ht.tree);
		btv.pack();
		btv.setVisible(true);

		for (int i = 0; i < 100; i++) {
			ht.put(i, "TEST" + i);
		}
		System.out.println(ht.get(10));
	}

	public static void parserTest() {
		Parser p = new Parser(new File("USDAFiles/FOOD_DES.TXT"), new File("USDAFiles/NUT_DATA.TXT"),
				new File("USDAFiles/NUTR_DEF.TXT"), new File("USDAFiles/FD_GROUP.TXT"),
				new File("USDAFiles/WEIGHT.TXT"), new File("USDAFiles/LANGUAL.txt"), new File("USDAFiles/LANGDESC.TXT"),
				new File("USDAFiles/FOOTNOTE.TXT"));

		p.parseData();
		BinaryTreeMap<Integer, FoodItem> foodItems = p.getFoodItemMap();
		DoublyLinkedList<FoodItem> selected = foodItems.selectAllItems(new Selector<FoodItem>() {
			@Override
			public boolean select(FoodItem foodItem){
				if(foodItem.getLongDescription().contains("Yogurt"))
					return true;
				return false;
			}
		});
		for(int i = 0; i < selected.size(); i++){
			System.out.println(selected.get(i));
		}
		System.out.println();
	}
}
