package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * =================================================================================================
 * || PLEASE DO NOT MODIFY THE PARSER OR ANY OF ITS SUBCLASSES UNTIL I GET THE CODE WORKING FULLY ||
 * =================================================================================================
 * 
 * HERE IS A FUNCTIONAL DESCRIPTION OF THE PARSER
 * It will read in the database files and create a structure as described:
 * 
 * Class Structure
 * Each food item in FOOD_DESC.TXT will have it's own FoodItem object. This will 
 * be represented as an array of FoodItems, and will be the only object that the user
 * can access in this parser class. Each FoodItem class contains every single field
 * possible; all the data in the file. This is necessary for the parser's function,
 * as will be described later on. The FoodItem also contains several objects which
 * contain data from other files. For example, the FoodItem contains a NutrientData
 * object, which stores a list of Nutrient objects, describing the nutritional data
 * of the FoodItem.
 * 
 * Food groups are read in from the food group file, and are not hardcoded into the code
 * since that will reduce portability of the code, as the food group file may change as
 * data changes.
 * 
 * The FoodItem class has a private constructor, since it would not make sense for a user
 * to create FoodItems of their own, as FoodItem is a helpful construct only to the parser,
 * and should not be instantiated anywhere else. Instead, a builder method is used to
 * construct the FoodItem.
 * 
 * Parser Function
 * The parser will read in data from the files and use the appropriate structures
 * to create objects containing the data, therefore loading all the data into
 * the RAM successfully. The data will be in a user (you) friendly format, with
 * getters and setters for all data fields.
 * The user will be able to call a method which returns a list of all food items
 * read in from the files.
 * 
 * @author Netdex
 *
 */
public class Parser {

	private HashMap<Integer, NutrientData> map_nutrientData = new HashMap<>();
	private HashMap<Integer, FoodItem> map_foodItem = new HashMap<>();
	private HashMap<Integer, FoodGroup> map_foodGroup = new HashMap<>();

	private File file_foodDesc;
	private File file_foodGroup;
	private File file_foodWeight;
	private File file_nutrientData;

	public Parser(File foodDesc, File nutrientData) {
		this.file_foodDesc = foodDesc;
		this.file_nutrientData = nutrientData;
	}

	public void parseData() {
		try {
			this.parseFoodDescriptions();
			this.parseNutrientData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseFoodDescriptions() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file_foodDesc));

		String line;
		while ((line = br.readLine()) != null) {
			line = line.replaceAll("~", "");
			// using .split now since stringtokenizer ignores empty values
			System.out.println(line);
			String[] items = line.split("\\^", -1);
			if(items.length < 13){
				System.out.println();
			}
			FoodItem fi = FoodItem.getInstance(Integer.parseInt(items[0]), 
					Integer.parseInt(items[1]), 
					items[2],
					items[3], 
					items[4], 
					items[5], 
					items[6].equals("Y"), 
					items[7], 
					Double.parseDouble(items[8]),
					items[9], 
					Double.parseDouble(items[10]), 
					Double.parseDouble(items[11]),
					Double.parseDouble(items[12]), 
					Double.parseDouble(items[13]));
			int id = fi.getNDB_No();
			map_foodItem.put(id, fi);
		}
		br.close();
	}

	private void parseNutrientData() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file_nutrientData));

		String line;
		while ((line = br.readLine()) != null) {
			line = line.replaceAll("~", "");
			String[] items = line.split("\\^", -1);
			Nutrient nutr = new Nutrient(
					Integer.parseInt(items[0]), 
					Integer.parseInt(items[1]),
					Double.parseDouble(items[2]), 
					Integer.parseInt(items[3]),
					Double.parseDouble(items[4]), 
					items[5], 
					items[6],
					Integer.parseInt(items[7]), 
					items[8], 
					Integer.parseInt(items[9]),
					Integer.parseInt(items[10]), 
					Integer.parseInt(items[11]),
					Double.parseDouble(items[12]), 
					Double.parseDouble(items[13]),
					Double.parseDouble(items[14]), 
					items[15], 
					items[16], 
					items[17]);
			FoodItem selectedFoodItem = map_foodItem.get(nutr.getNdbNo());
			if (selectedFoodItem.getNutrientData() == null) {
				selectedFoodItem.setNutrientData(new NutrientData());
			}
			NutrientData nd = selectedFoodItem.getNutrientData();
			nd.addNutrient(nutr);
		}
	}

	public static FoodItem[] parseFoodData(File file) {
		ArrayList<FoodItem> items = new ArrayList<FoodItem>();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		return null;

	}
}
