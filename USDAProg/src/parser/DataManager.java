package parser;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

import gui.GUI;
import parser.parsables.FoodGroup;
import parser.parsables.FoodItem;
import parser.util.BinaryTreeMap;

public class DataManager {

	private static DataManager instance;

	private Parser parser;
	private GUI gui;

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
		parser = new Parser(files, gui);
		parser.parseData();
	}

	public void initAsync(File... files) {
		parser = new Parser(files, gui);
		parser.parseDataAsync();
	}

	public FoodGroup[] getFoodGroups() {
		return parser.getFoodGroups().toArray(FoodGroup.SAMPLE);
	}

	public Parser getParser(){
		return parser;
	}
	public FoodItem[] searchForItem(String[] keys) {
		if(keys.length == 0)
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
			FoodItem fi = foodItems[foodIdx];
			String[] tokens = fi.getLongDescription().replaceAll("[^A-Za-z ]", "")
					.split(" ");
			boolean relevant = false;
			for (int keyIdx = 0; keyIdx < keys.length; keyIdx++) {
				// Check if the phrase contains it
				if (fi.getLongDescription().toLowerCase()
						.contains(keys[keyIdx].toLowerCase())){
					score += 0.5;
					relevant = true;
				}
				
				// Check if the common name contains the key
				if(fi.getCommonName().toLowerCase().contains(keys[keyIdx].toLowerCase())){
					score += 1;
					relevant = true;
				}
				
				// Check for whole tokens of the key
				boolean found = false;
				for (int i = 0; i < tokens.length; i++){
					if (tokens[i].equalsIgnoreCase(keys[keyIdx])) {
						found = true;
						break;
					}
				}
				if (found)
					score += 0.5;
			}
			if(relevant){
				score += 1.0 / fi.getLongDescription().length();
			}
			if (score > 0)
				map_results.put(fi, score);
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
//		for(FoodItem s : matched){
//			System.out.println(s + " " + map_results.get(s));
//		}
		return Arrays.copyOf(matched, Math.min(matched.length, 500));
	}

	public static DataManager getInstance() {
		if (instance == null)
			instance = new DataManager();
		return instance;
	}

}
