package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Parser {

	private HashMap<Integer, NutrientData> map_nutrientData;
	private HashMap<Integer, FoodItem> map_foodItem;
	private HashMap<Integer, FoodGroup> map_foodGroup;

	private File file_foodDesc;
	private File file_foodGroup;
	private File file_foodWeight;
	private File file_nutrientData;

	public Parser(File foodDesc, File foodGroup, File foodWeight,
			File nutrientData) {

	}

	private void parseFoodDescriptions() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(file_foodDesc));

		String line;
		while ((line = br.readLine()) != null) {
			StringTokenizer st = new StringTokenizer(line, "^");
			String[] arr = line.split("\\^");
			FoodItem foodItem = new FoodItem(
					Integer.parseInt(st.nextToken()),
					Integer.parseInt(st.nextToken()),
					st.nextToken(), // you're going to have to get rid of the tildes
					st.nextToken(),
					st.nextToken(),
					st.nextToken(),
					st.nextToken()); // cross reference and get the food group and then add it here
			int id = foodItem.getNDB_No();
			map_foodItem.put(id, foodItem);
		}
		br.close();
	}

	public static FoodItem[] parseFoodData(File file) {
		ArrayList<FoodItem> items = new ArrayList<FoodItem>();

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		return null;

	}
}
