package parser;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import parser.gui.LoadingWindow;
import parser.parsables.FoodGroup;
import parser.parsables.FoodItem;
import parser.util.BinaryTreeMap;

public class DataManager {

	private LoadingWindow lwindow;
	private static DataManager instance;

	private Parser parser;

	private DataManager() {
		lwindow = new LoadingWindow();
	}

	/**
	 * Initialize the parser with the given data files.
	 * 
	 * @param files In this order: File foodDesc, File nutrientData, File
	 *            nutrientDescription, File foodGroup, File foodWeight, File
	 *            langual, File langualDesc, File footnotes
	 */
	public void init(File... files) {
		parser = new Parser(files, lwindow);
		lwindow.setVisible(true);
		parser.parseData();
	}

	public void initAsync(File... files) {
		parser = new Parser(files, lwindow);
		lwindow.setVisible(true);
		parser.parseDataAsync();
	}

	public FoodGroup[] getFoodGroups() {
		return parser.getFoodGroups().toArray();
	}

	public FoodItem[] searchForItem(String[] keys) {
		// Check if the user is searching for an NDB number
		if (keys.length == 1) {
			if (keys[0].matches("[0-9]{5}")) {
				return new FoodItem[] { parser.getFoodItemMap().get(Integer.parseInt(keys[0])) };
			}
		}
		// Get the map of food items
		BinaryTreeMap<Integer, FoodItem> map_foodItems = parser.getFoodItemMap();
		// Extract the list of food items from the map
		FoodItem[] foodItems = map_foodItems.getAllValues().toArray();
		// Create a map to store the score of each food item
		BinaryTreeMap<FoodItem, Double> map_results = new BinaryTreeMap<>();
		for (int foodIdx = 0; foodIdx < foodItems.length; foodIdx++) {
			// Rank each food item by appearance of keys in the text
			double count = 0;
			FoodItem fi = foodItems[foodIdx];
			String[] tokens = foodItems[foodIdx].getLongDescription().replaceAll("[^A-Za-z ]", "")
					.split(" ");
			for (int keyIdx = 0; keyIdx < keys.length; keyIdx++) {
				if (foodItems[foodIdx].getLongDescription().toLowerCase()
						.contains(keys[keyIdx].toLowerCase()))
					count += 0.5;
				boolean found = false;
				for (int i = 0; i < tokens.length; i++)
					if (tokens[i].equalsIgnoreCase(keys[keyIdx])) {
						found = true;
						break;
					}
				if (found)
					count += 0.5;
			}
			if (count > 0)
				map_results.put(fi, count);
		}
		// System.out.println(map_results);
		// Get all the food items which had a score of at least 1
		FoodItem[] matched = map_results.getAllKeys().toArray();
		if (matched == null)
			return new FoodItem[0];
		// Sort the list of food items by their score
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
		// Return a list of matches capped at 25
		return Arrays.copyOf(matched, Math.min(matched.length, 25));
	}

	public static DataManager getInstance() {
		if (instance == null)
			instance = new DataManager();
		return instance;
	}

}
