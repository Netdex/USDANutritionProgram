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
 * PLEASE DONT CHANGE THIS SHIT YET IT IS STILL A WORK IN PROGRESS
 * 
 * @author Netdex
 *
 */
public class Parser {

	private HashMap<Integer, NutrientData> map_nutrientData;
	private HashMap<Integer, FoodItem> map_foodItem;
	private HashMap<Integer, FoodGroup> map_foodGroup;

	private File file_foodDesc;
	private File file_foodGroup;
	private File file_foodWeight;
	private File file_nutrientData;

	public Parser(File foodDesc, File foodGroup, File foodWeight, File nutrientData) {

	}

	private void parseFoodDescriptions() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file_foodDesc));

		String line;
		while ((line = br.readLine()) != null) {
			line = line.replaceAll("~", "");
			StringTokenizer st = new StringTokenizer(line, "^");
			FoodItem foodItem = new FoodItem(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
					st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken(), st.nextToken(), null);
			int id = foodItem.getNDB_No();
			map_foodItem.put(id, foodItem);
		}
		br.close();
	}

	private void parseNutrientData() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file_nutrientData));

		String line;
		while ((line = br.readLine()) != null) {
			line = line.replaceAll("~", "");
			StringTokenizer st = new StringTokenizer(line, "^");
			Nutrient nutr = new Nutrient(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
					Double.parseDouble(st.nextToken()), Integer.parseInt(st.nextToken()),
					Double.parseDouble(st.nextToken()), st.nextToken(), st.nextToken(),
					Integer.parseInt(st.nextToken()), st.nextToken(), Integer.parseInt(st.nextToken()),
					Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()),
					Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()),
					Double.parseDouble(st.nextToken()), st.nextToken(), st.nextToken(), st.nextToken());
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
