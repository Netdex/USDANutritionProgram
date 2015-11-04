package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

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
	
	// Various maps for storing indexes to temporary data
	private HashMap<Integer, FoodItem> map_foodItem = new HashMap<>();
	
	private HashMap<Integer, NutrientDescription> map_nutrDesc = new HashMap<>();
	private HashMap<Integer, FoodGroup> map_foodGroup = new HashMap<>();
	private HashMap<Integer, FoodWeight> map_foodWeight = new HashMap<>();
	
	// Various file handles
	private File file_foodDesc;
	private File file_foodGroup;
	private File file_foodWeight;
	private File file_nutrientData;
	private File file_nutrientDescription;

	public Parser(File foodDesc, File nutrientData, File nutrientDescription, File foodGroup, File foodWeight) {
		this.file_foodDesc = foodDesc;
		this.file_nutrientData = nutrientData;
		this.file_nutrientDescription = nutrientDescription;
		this.file_foodGroup = foodGroup;
		this.file_foodWeight = foodWeight;
	}

	public void parseData() {
		try {
			System.out.println("PARSING FOOD GROUPS");
			this.parseFoodGroups();
			System.out.println("PARSING FOOD WEIGHTS");
			this.parseFoodWeights();
			System.out.println("PARSING FOOD DESCRIPTIONS");
			this.parseFoodDescriptions();
			System.out.println("PARSING NUTRIENT DEFINITIONS");
			this.parseNutrientDefinitions();
			System.out.println("PARSING NUTRIENT DATA");
			this.parseNutrientData();
			System.out.println("DONE");
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
	}

	/**
	 * Parses all the food weights
	 * @throws IOException
	 * @throws InvalidParseDataException
	 */
	private void parseFoodWeights() throws IOException, InvalidParseDataException {
		BufferedReader br = new BufferedReader(new FileReader(file_foodWeight));

		String line;
		while ((line = br.readLine()) != null) {
			line = line.replace("~", "");
			// using .split now since stringtokenizer ignores empty values
			String[] items = line.split("\\^", -1);
			FoodWeight foodWeight = new FoodWeight();
			foodWeight.parse(items);
			map_foodWeight.put(foodWeight.getNutrientDatabankNumber(), foodWeight);
		}
		br.close();
	}
	
	/**
	 * Parses all the food groups
	 * @throws IOException
	 * @throws InvalidParseDataException
	 */
	private void parseFoodGroups() throws IOException, InvalidParseDataException {
		BufferedReader br = new BufferedReader(new FileReader(file_foodGroup));

		String line;
		while ((line = br.readLine()) != null) {
			line = line.replace("~", "");
			// using .split now since stringtokenizer ignores empty values
			String[] items = line.split("\\^", -1);
			FoodGroup foodGroup = new FoodGroup();
			foodGroup.parse(items);
			map_foodGroup.put(foodGroup.getFoodGroupID(), foodGroup);
		}
		br.close();
	}
	/**
	 * Reads all the food descriptions and places it into a lookup table
	 * Sets the proper food group of the item
	 * @throws IOException
	 * @throws InvalidParseDataException
	 */
	private void parseFoodDescriptions() throws IOException, InvalidParseDataException {
		BufferedReader br = new BufferedReader(new FileReader(file_foodDesc));

		String line;
		while ((line = br.readLine()) != null) {
			line = line.replace("~", "");
			// using .split now since stringtokenizer ignores empty values
			String[] items = line.split("\\^", -1);
			FoodItem foodItem = new FoodItem();
			foodItem.parse(items);
			foodItem.setFoodGroup(map_foodGroup.get(foodItem.getFoodGroupID()));
			foodItem.setWeightInfo(map_foodWeight.get(foodItem.getNutrientDatabankNumber()));
			int id = foodItem.getNutrientDatabankNumber();
			map_foodItem.put(id, foodItem);
		}
		br.close();
	}

	/**
	 * Parses all the nutrient definitions
	 * @throws IOException
	 * @throws InvalidParseDataException
	 */
	private void parseNutrientDefinitions() throws IOException, InvalidParseDataException {
		BufferedReader br = new BufferedReader(new FileReader(file_nutrientDescription));
		
		String line;
		while((line = br.readLine()) != null){
			line = line.replace("~", "");
			String[] items = line.split("\\^", -1);
			NutrientDescription nd = new NutrientDescription();
			nd.parse(items);
			map_nutrDesc.put(nd.getNutrientNumber(), nd);
		}
		br.close();
	}
	/**
	 * Reads the nutrient data, and adds it to the appropriate food item
	 * Sets the nutrient definition of the nutrient according to the table
	 * @throws IOException
	 * @throws InvalidParseDataException
	 */
	private void parseNutrientData() throws IOException, InvalidParseDataException {
		BufferedReader br = new BufferedReader(new FileReader(file_nutrientData));

		String line;
		while ((line = br.readLine()) != null) {
			line = line.replace("~", "");
			String[] items = line.split("\\^", -1);
			Nutrient nutr = new Nutrient();
			nutr.parse(items);
			nutr.setNutrientDescription(map_nutrDesc.get(nutr.getNutrNo()));
			FoodItem selectedFoodItem = map_foodItem.get(nutr.getNdbNo());
			NutrientData nd = selectedFoodItem.getNutrientData();
			nd.addNutrient(nutr);
		}
		br.close();
	}
}
