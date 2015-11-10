package parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import gui.GUI;
import parser.gui.LoadingWindow;
import parser.parsables.FoodGroup;
import parser.parsables.FoodItem;
import parser.parsables.FoodWeight;
import parser.parsables.Footnote;
import parser.parsables.LanguaL;
import parser.parsables.LanguaLDescription;
import parser.parsables.LanguaLGroup;
import parser.parsables.Nutrient;
import parser.parsables.NutrientData;
import parser.parsables.NutrientDescription;
import parser.util.BinaryTreeMap;
import parser.util.DoublyLinkedList;

/**
 * Creates data structures out of the given files
 * 
 * @author Netdex
 *
 */
public class Parser {

	// Various maps for storing indexes to temporary data
	private BinaryTreeMap<Integer, FoodItem> map_foodItems = new BinaryTreeMap<>();

	private BinaryTreeMap<Integer, NutrientData> map_nutrData = new BinaryTreeMap<>();
	private BinaryTreeMap<Integer, NutrientDescription> map_nutrDesc = new BinaryTreeMap<>();
	private BinaryTreeMap<Integer, FoodGroup> map_foodGroup = new BinaryTreeMap<>();
	private BinaryTreeMap<Integer, FoodWeight> map_foodWeight = new BinaryTreeMap<>();
	private BinaryTreeMap<Integer, LanguaLGroup> map_langualGroup = new BinaryTreeMap<>();
	private BinaryTreeMap<String, LanguaLDescription> map_langualDesc = new BinaryTreeMap<>();
	private BinaryTreeMap<Integer, Footnote> map_footnote = new BinaryTreeMap<>();

	/**
	 * In this order: 0 File foodDesc, 1 File nutrientData, 2 File
	 * nutrientDescription, 3 File foodGroup, 4 File foodWeight, 5 File langual,
	 * 6 File langualDesc, 7 File footnotes
	 */
	private File[] dataFiles;

	private long totalFileSize = 0;
	private long processedFileSize = 0;
	private GUI gui;

	public Parser(File[] files, GUI gui) {
		this.dataFiles = files;
		this.gui = gui;
		for (File f : files) {
			totalFileSize += f.length();
		}
	}

	/**
	 * EXPENSIVE OPERATION
	 * 
	 * @return
	 */
	public DoublyLinkedList<FoodItem> getParsedData() {
		return map_foodItems.getAllValues();
	}

	public BinaryTreeMap<Integer, FoodItem> getFoodItemMap() {
		return map_foodItems;
	}

	/**
	 * Parses all the data in the given files, but asynchronously
	 */
	public void parseDataAsync() {
		Thread[] threads = new Thread[5];
		new Thread() {
			public void run() {
				long start = System.currentTimeMillis();
				try {
					threads[0] = new Thread() {
						public void run() {
							try {
								System.err.println("PARSING FOOD GROUPS");
								parseFoodGroups();
							} catch (Exception e) {

							}
						}
					};
					threads[1] = new Thread() {
						public void run() {
							try {
								System.err.println("PARSING FOOD WEIGHTS");
								parseFoodWeights();
							} catch (Exception e) {

							}
						}
					};
					threads[2] = new Thread() {
						public void run() {
							try {
								System.err.println("PARSING FOOTNOTES");
								parseFootnotes();
							} catch (Exception e) {

							}
						}
					};
					threads[3] = new Thread() {
						public void run() {
							try {
								System.err.println("PARSING NUTRIENT DEFINITIONS");
								parseNutrientDefinitions();
								System.err.println("PARSING NUTRIENT DATA");
								parseNutrientData();
							} catch (Exception e) {

							}
						}
					};
					threads[4] = new Thread() {
						public void run() {
							try {
								System.err.println("PARSING LANGUAL DESCRIPTIONS");
								parseLanguaLDescriptions();
								System.err.println("PARSING LANGUAL DATA");
								parseLanguaL();
							} catch (Exception e) {

							}
						}
					};
					for (int i = 0; i < 5; i++)
						threads[i].start();
					for (int i = 0; i < 5; i++)
						threads[i].join();
					System.err.println("PARSING FOOD DESCRIPTIONS");
					parseFoodDescriptions();
					System.err.println("DONE");
					gui.getPanelManager().LOADING_PERCENTAGE = -1;
				} catch (Exception e) {
					e.printStackTrace();
				}
				long end = System.currentTimeMillis() - start;
				System.err.println("Took " + end + "ms");
			}
		}.start();

	}

	/**
	 * Parses all the data in the given files
	 */
	public void parseData() {
		try {
			long start = System.currentTimeMillis();

			System.err.println("PARSING FOOD GROUPS");
			this.parseFoodGroups();
			System.err.println("PARSING FOOD WEIGHTS");
			this.parseFoodWeights();
			System.err.println("PARSING FOOTNOTES");
			this.parseFootnotes();
			System.err.println("PARSING NUTRIENT DEFINITIONS");
			this.parseNutrientDefinitions();
			System.err.println("PARSING NUTRIENT DATA");
			this.parseNutrientData();
			System.err.println("PARSING LANGUAL DESCRIPTIONS");
			this.parseLanguaLDescriptions();
			System.err.println("PARSING LANGUAL DATA");
			this.parseLanguaL();
			System.err.println("PARSING FOOD DESCRIPTIONS");
			this.parseFoodDescriptions();
			System.err.println("DONE");
			gui.getPanelManager().LOADING_PERCENTAGE = -1;

			long end = System.currentTimeMillis() - start;
			System.err.println("Took " + end + "ms");

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println();
	}

	private void parseFootnotes() throws IOException, InvalidParseDataException {
		BufferedReader br = new BufferedReader(new FileReader(dataFiles[7]));

		String line;
		while ((line = br.readLine()) != null) {
			processedFileSize += line.getBytes().length + 1;
			line = line.replace("~", "");
			// using .split now since stringtokenizer ignores empty values
			String[] items = line.split("\\^", -1);
			Footnote footnote = new Footnote().parse(items);
			map_footnote.put(footnote.getNdbNo(), footnote);
			updatePercentage();
			gui.getPanelManager().repaint();
		}
		br.close();
	}

	/**
	 * Parses descriptions for LanguaL definitions
	 * 
	 * @throws IOException
	 * @throws InvalidParseDataException
	 */
	private void parseLanguaLDescriptions() throws IOException, InvalidParseDataException {
		BufferedReader br = new BufferedReader(new FileReader(dataFiles[6]));

		String line;
		while ((line = br.readLine()) != null) {
			processedFileSize += line.getBytes().length + 1;
			line = line.replace("~", "");
			// using .split now since stringtokenizer ignores empty values
			String[] items = line.split("\\^", -1);
			LanguaLDescription lld = new LanguaLDescription().parse(items);
			map_langualDesc.put(lld.getFactorCode(), lld);
			updatePercentage();
			gui.getPanelManager().repaint();
		}
		br.close();
	}

	/**
	 * Parses LanguaL names for food items
	 * 
	 * @throws IOException
	 * @throws InvalidParseDataException
	 */
	private void parseLanguaL() throws IOException, InvalidParseDataException {
		BufferedReader br = new BufferedReader(new FileReader(dataFiles[5]));

		String line;
		while ((line = br.readLine()) != null) {
			processedFileSize += line.getBytes().length + 1;
			line = line.replace("~", "");
			// using .split now since stringtokenizer ignores empty values
			String[] items = line.split("\\^", -1);
			LanguaL ll = new LanguaL().parse(items);
			ll.setLangualDescription(map_langualDesc.get(ll.getFactorCode()));
			if (map_langualGroup.get(ll.getNDBNo()) == null)
				map_langualGroup.put(ll.getNDBNo(), new LanguaLGroup());
			map_langualGroup.get(ll.getNDBNo()).addLanguaL(ll);
			updatePercentage();
			gui.getPanelManager().repaint();
		}
		br.close();
	}

	/**
	 * Parses all the food weights
	 * 
	 * @throws IOException
	 * @throws InvalidParseDataException
	 */
	private void parseFoodWeights() throws IOException, InvalidParseDataException {
		BufferedReader br = new BufferedReader(new FileReader(dataFiles[4]));

		String line;
		while ((line = br.readLine()) != null) {
			processedFileSize += line.getBytes().length + 1;
			line = line.replace("~", "");
			// using .split now since stringtokenizer ignores empty values
			String[] items = line.split("\\^", -1);
			FoodWeight foodWeight = new FoodWeight().parse(items);
			map_foodWeight.put(foodWeight.getNDBNo(), foodWeight);
			updatePercentage();
			gui.getPanelManager().repaint();
		}
		br.close();
	}

	/**
	 * Parses all the food groups
	 * 
	 * @throws IOException
	 * @throws InvalidParseDataException
	 */
	private void parseFoodGroups() throws IOException, InvalidParseDataException {
		BufferedReader br = new BufferedReader(new FileReader(dataFiles[3]));

		String line;
		while ((line = br.readLine()) != null) {
			processedFileSize += line.getBytes().length + 1;
			line = line.replace("~", "");
			// using .split now since stringtokenizer ignores empty values
			String[] items = line.split("\\^", -1);
			FoodGroup foodGroup = new FoodGroup().parse(items);
			map_foodGroup.put(foodGroup.getFoodGroupID(), foodGroup);
			updatePercentage();
			gui.getPanelManager().repaint();
		}
		br.close();
	}

	/**
	 * Reads all the food descriptions and places it into a lookup table Sets
	 * the proper food group of the item Sets the proper nutrients of the item
	 * 
	 * @throws IOException
	 * @throws InvalidParseDataException
	 */
	private void parseFoodDescriptions() throws IOException, InvalidParseDataException {
		BufferedReader br = new BufferedReader(new FileReader(dataFiles[0]));

		String line;
		while ((line = br.readLine()) != null) {
			processedFileSize += line.getBytes().length + 1;
			line = line.replace("~", "");
			// using .split now since stringtokenizer ignores empty values
			String[] items = line.split("\\^", -1);
			FoodItem foodItem = new FoodItem().parse(items);

			// Set the appropriate items in the food item
			int ndbNo = foodItem.getNDBNo();
			foodItem.setFoodGroup(map_foodGroup.get(foodItem.getFoodGroupID()));
			foodItem.setWeightInfo(map_foodWeight.get(ndbNo));
			foodItem.setNutrientData(map_nutrData.get(ndbNo));
			foodItem.setLangualGroup(map_langualGroup.get(ndbNo));
			foodItem.setFootnotes(map_footnote.get(ndbNo));

			foodItem.getFoodGroup().addFood(foodItem);
			map_foodItems.put(ndbNo, foodItem);
			updatePercentage();
			gui.getPanelManager().repaint();
		}
		br.close();
	}

	/**
	 * Parses all the nutrient definitions
	 * 
	 * @throws IOException
	 * @throws InvalidParseDataException
	 */
	private void parseNutrientDefinitions() throws IOException, InvalidParseDataException {
		BufferedReader br = new BufferedReader(new FileReader(dataFiles[2]));

		String line;
		while ((line = br.readLine()) != null) {
			processedFileSize += line.getBytes().length + 1;
			line = line.replace("~", "");
			String[] items = line.split("\\^", -1);
			NutrientDescription nd = new NutrientDescription().parse(items);
			map_nutrDesc.put(nd.getNutrientNumber(), nd);
			updatePercentage();
			gui.getPanelManager().repaint();
		}
		br.close();
	}

	/**
	 * Reads the nutrient data, and adds it to the appropriate food item Sets
	 * the nutrient definition of the nutrient according to the table
	 * 
	 * @throws IOException
	 * @throws InvalidParseDataException
	 */
	private void parseNutrientData() throws IOException, InvalidParseDataException {
		BufferedReader br = new BufferedReader(new FileReader(dataFiles[1]));

		String line;
		while ((line = br.readLine()) != null) {
			processedFileSize += line.getBytes().length + 1;
			line = line.replace("~", "");
			String[] items = line.split("\\^", -1);
			Nutrient nutr = new Nutrient().parse(items);
			nutr.setNutrientDescription(map_nutrDesc.get(nutr.getNutrNo()));
			NutrientData nd = map_nutrData.get(nutr.getNDBNo());
			if (nd == null)
				map_nutrData.put(nutr.getNDBNo(), new NutrientData());
			map_nutrData.get(nutr.getNDBNo()).addNutrient(nutr);
			updatePercentage();
			gui.getPanelManager().repaint();
		}
		br.close();
	}

	public void updatePercentage() {
		gui.getPanelManager().LOADING_PERCENTAGE = (int) (100.0 * processedFileSize
				/ totalFileSize);
		gui.getPanelManager().repaint();
	}

	public DoublyLinkedList<FoodGroup> getFoodGroups() {
		return map_foodGroup.getAllValues();
	}
}
