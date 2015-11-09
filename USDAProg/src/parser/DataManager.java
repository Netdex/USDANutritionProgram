package parser;

import java.io.File;

import parser.parsables.FoodGroup;

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
	
	public FoodGroup[] getFoodGroups(){
		return parser.getFoodGroups().toArray();
	}
	
	public static DataManager getInstance(){
		if(instance == null)
			instance = new DataManager();
		return instance;
	}
	
}
