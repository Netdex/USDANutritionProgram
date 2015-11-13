package parser;

import gui.GUI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Comparator;

import parser.parsables.FoodGroup;
import parser.parsables.FoodItem;
import parser.parsables.FoodWeight;
import parser.parsables.Footnote;
import parser.parsables.LanguaLGroup;
import parser.parsables.NutrientData;
import parser.util.BinaryTreeMap;
import parser.util.DoublyLinkedList;

public class DataManager {

	private static DataManager instance;

	private DoublyLinkedList<Runnable> hooks = new DoublyLinkedList<>();

	private Parser parser;
	private GUI gui;
	private File[] files;

	private DataManager() {

	}

	public void bindProgressDisplay(GUI gui) {
		this.gui = gui;
	}

	/**
	 * Initialize the parser with the given data files.
	 * 
	 * @param files In this order: File foodDesc, File nutrientData, File
	 *            nutrientDescription, File foodGroup, File foodWeight, File
	 *            langual, File langualDesc, File footnotes
	 */
	public void init(File... files) {
		this.files = files;
		parser = new Parser(files, gui);
		new Thread() {
			public void run() {
				parser.parseData();
				for (int i = 0; i < hooks.size(); i++) {
					Runnable r = hooks.get(i);
					r.run();
				}
			}
		}.start();
	}

	/**
	 * Runs code after the parsing is done
	 * 
	 * @param r The runnable containing the event to run
	 */
	public void registerSyncEvent(Runnable r) {
		hooks.add(r);
	}

	public void addFoodItem(FoodItem fi) {
		FoodGroup fg = fi.getFoodGroup();
		NutrientData nd = fi.getNutrientData();
		Footnote fn = fi.getFootnotes();
		LanguaLGroup lg = fi.getLangualGroup();
		FoodWeight wi = fi.getWeightInfo();

		try {
			PrintStream ps_foodItem = new PrintStream(new FileOutputStream(files[0], true));
			PrintStream ps_foodGroup = new PrintStream(new FileOutputStream(files[3], true));
			PrintStream ps_nutrientData = new PrintStream(new FileOutputStream(files[1], true));
			PrintStream ps_nutrientDesc = new PrintStream(new FileOutputStream(files[2], true));
			PrintStream ps_footNotes = new PrintStream(new FileOutputStream(files[7], true));
			PrintStream ps_languals = new PrintStream(new FileOutputStream(files[5], true));
			PrintStream ps_langualDesc = new PrintStream(new FileOutputStream(files[6], true));
			PrintStream ps_weightInfo = new PrintStream(new FileOutputStream(files[4], true));
			ps_foodItem.println("");
			
		} catch (Exception e) {

		}
	}

	public FoodGroup[] getFoodGroups() {
		return parser.getFoodGroups().toArray(FoodGroup.SAMPLE);
	}

	public Parser getParser() {
		return parser;
	}

	public FoodItem[] searchForItem(String[] keys) {
		if (keys.length == 0)
			return new FoodItem[0];
		// Check if the user is searching for an NDB number
		if (keys.length == 1) {
			if (keys[0].matches("[0-9]{5}")) {
				if (parser.getFoodItemMap().get(Integer.parseInt(keys[0])) != null)
					return new FoodItem[] { parser.getFoodItemMap().get(Integer.parseInt(keys[0])) };
				else
					return new FoodItem[0];
			}
		}
		// Get the map of food items
		BinaryTreeMap<Integer, FoodItem> map_foodItems = parser.getFoodItemMap();
		// Extract the list of food items from the map
		FoodItem[] foodItems = map_foodItems.getAllValues().toArray(FoodItem.SAMPLE);
		// Create a map to store the score of each food item
		BinaryTreeMap<FoodItem, Double> map_results = new BinaryTreeMap<>();
		for (int foodIdx = 0; foodIdx < foodItems.length; foodIdx++) {
			// Rank each food item by appearance of keys in the text
			double score = 0;
			FoodItem foodItem = foodItems[foodIdx];
			String[] tokens = foodItem.getLongDescription().replaceAll("[^A-Za-z ]", "").split(" ");
			boolean relevant = false;
			for (int keyIdx = 0; keyIdx < Math.min(keys.length, 5); keyIdx++) {
				// Check if the phrase contains it
				if (foodItem.getLongDescription().toLowerCase().contains(keys[keyIdx].toLowerCase())) {
					score += 0.5;
					relevant = true;
				}

				// Check if the common name contains the key
				if (foodItem.getCommonName().toLowerCase().contains(keys[keyIdx].toLowerCase())) {
					score += 1;
					relevant = true;
				}
				
				if(foodItem.getLongDescription().toLowerCase().startsWith(keys[keyIdx].toLowerCase())){
					score += 0.2;
					relevant = true;
				}
				
				// Check for whole tokens of the key
				boolean found = false;
				for (int i = 0; i < tokens.length; i++) {
					String currentToken = tokens[i];
					if(tokens[i].toLowerCase().endsWith("s") && !keys[keyIdx].toLowerCase().endsWith("s")){
						currentToken = currentToken.substring(0, currentToken.length() - 1);
					}
					if (currentToken.equalsIgnoreCase(keys[keyIdx])) {
						found = true;
						break;
					}
				}
				if (found)
					score += 1.1;
			}
			if (relevant) {
				score += 1.0 / foodItem.getLongDescription().length();
			}
			if (score > 0)
				map_results.put(foodItem, score);
		}
		// System.out.println(map_results);
		// Get all the food items which had a score of at least 1
		FoodItem[] matched = map_results.getAllKeys().toArray(FoodItem.SAMPLE);
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
		// for(FoodItem s : matched){
		// System.out.println(s + " " + map_results.get(s));
		// }
		return Arrays.copyOf(matched, Math.min(matched.length, 500));
	}

	public static DataManager getInstance() {
		if (instance == null)
			instance = new DataManager();
		return instance;
	}

}
