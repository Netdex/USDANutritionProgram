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
	
	public Parser(File foodDesc, File foodGroup, File foodWeight, File nutrientData){
		
	}
	private void parseFoodDescriptions() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(file_foodDesc));
		
		String line;
		while((line = br.readLine()) != null){
			StringTokenizer st = new StringTokenizer(line, "^");
			String[] arr = line.split("\\^");
			FoodItem fi = FoodItem.getInstance(
					Integer.parseInt(st.nextToken()), 
					Integer.parseInt(st.nextToken()), 
					st.nextToken(), 
					st.nextToken(), 
					st.nextToken(), 
					st.nextToken(), 
					st.nextToken().equals("Y"), 
					st.nextToken(), 
					Double.parseDouble(st.nextToken()), 
					st.nextToken(),
					Double.parseDouble(st.nextToken()), 
					Double.parseDouble(st.nextToken()), 
					Double.parseDouble(st.nextToken()), 
					Double.parseDouble(st.nextToken()));
			int id = fi.getNDB_No();
			map_foodItem.put(id, fi);
		}
		br.close();
	}
	
	public static FoodItem[] parseFoodData(File file){
		ArrayList<FoodItem> items = new ArrayList<FoodItem>();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
		return null;
		
	}
}
