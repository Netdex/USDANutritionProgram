package parser;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;

import parser.parsables.FoodGroup;
import parser.parsables.FoodItem;
import parser.util.BinaryTreeMap;

public class DataManager {

	private static DataManager instance;

	private Parser parser;

	private DataManager() {

	}

	/**
	 * Initialize the parser with the given data files.
	 * 
	 * @param files In this order: File foodDesc, File nutrientData, File
	 *            nutrientDescription, File foodGroup, File foodWeight, File
	 *            langual, File langualDesc, File footnotes
	 */
	public void init(File... files) {
		parser = new Parser(files[0], files[1], files[2], files[3], files[4], files[5], files[6],
				files[7]);
		parser.parseData();
	}

	public void initAsync(File... files) {
		parser = new Parser(files[0], files[1], files[2], files[3], files[4], files[5], files[6],
				files[7]);
		parser.parseDataAsync();
	}

	public FoodGroup[] getFoodGroups() {
		return parser.getFoodGroups().toArray();
	}

	public FoodItem[] searchForItem(String[] keys) {
		BinaryTreeMap<Integer, FoodItem> map_foodItems = parser.getFoodItemMap();
		FoodItem[] foodItems = map_foodItems.getAllValues().toArray();
		BinaryTreeMap<FoodItem, Double> map_results = new BinaryTreeMap<>();
		for (int foodIdx = 0; foodIdx < foodItems.length; foodIdx++) {
			double count = 0;
			FoodItem fi = foodItems[foodIdx];
			String[] tokens = foodItems[foodIdx].getLongDescription().replaceAll("[^A-Za-z ]", "").split(" ");
			for (int keyIdx = 0; keyIdx < keys.length; keyIdx++) {
				if (foodItems[foodIdx].getLongDescription().toLowerCase()
						.contains(keys[keyIdx].toLowerCase()))
					count += 0.5;
				
				boolean found = false;
				for(int i = 0; i < tokens.length; i++)
					if(tokens[i].equalsIgnoreCase(keys[keyIdx])){
						found = true;
						break;
					}
				if(found)
					count += 0.5;
			}
			if (count > 0)
				map_results.put(fi, count);
		}
//		System.out.println(map_results);
		FoodItem[] matched = map_results.getAllKeys().toArray();
		if (matched == null)
			return new FoodItem[0];
		Arrays.sort(matched, new Comparator<FoodItem>() {
			@Override
			public int compare(FoodItem a, FoodItem b) {
				if (map_results.get(b) > map_results.get(a))
					return 1;
				else if (map_results.get(b) < map_results.get(a))
					return -1;
				return 0;
			}
		});
		return Arrays.copyOf(matched, Math.min(matched.length, 25));
	}

	public static DataManager getInstance() {
		if (instance == null)
			instance = new DataManager();
		return instance;
	}

}
