package parser;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import parser.parsables.FoodGroup;
import parser.parsables.FoodItem;
import parser.util.BinaryTreeMap;
import parser.util.DoublyLinkedList;

public class DataManager {

	private static DataManager instance;
	
	private Parser parser;
	
	private DataManager(){
		
	}
	
	/**
	 * Initialize the parser with the given data files.
	 * @param files In this order: File foodDesc, File nutrientData, File nutrientDescription, File foodGroup, File foodWeight,
			File langual, File langualDesc, File footnotes
	 */
	public void init(File... files){
		parser = new Parser(files[0], files[1], files[2], files[3], files[4], files[5], files[6], files[7]);
		parser.parseData();
	}
	public void initAsync(File... files){
		parser = new Parser(files[0], files[1], files[2], files[3], files[4], files[5], files[6], files[7]);
		parser.parseDataAsync();
	}
	
	public FoodGroup[] getFoodGroups(){
		return parser.getFoodGroups().toArray();
	}
	
	public FoodItem[] searchForItem(String[] keys){
		BinaryTreeMap<Integer, FoodItem> map_foodItems = parser.getFoodItemMap();
		DoublyLinkedList<FoodItem> foodItems = map_foodItems.getAllValues();
		BinaryTreeMap<FoodItem, Integer> map_results = new BinaryTreeMap<>();
		for(int i = 0 ; i < foodItems.size(); i++){
			int count = 0;
			FoodItem fi = foodItems.get(i);
			for(int j = 0; j < keys.length; j++){
				if(foodItems.get(i).getLongDescription().toLowerCase().contains(keys[j].toLowerCase()))
					count++;
			}
			if(count > 0)
				map_results.put(fi, count);
		}
		FoodItem[] matched = map_results.getAllKeys().toArray();
		if(matched == null)
			return new FoodItem[0];
		Arrays.sort(matched, new Comparator<FoodItem>(){
			@Override
			public int compare(FoodItem a, FoodItem b) {
				return map_results.get(b) - map_results.get(a);
			}
		});
		return Arrays.copyOf(matched, Math.min(matched.length, 25));
	}
	
	public static DataManager getInstance(){
		if(instance == null)
			instance = new DataManager();
		return instance;
	}
	
}
